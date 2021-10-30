/*
 * Copyright 2021 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.todometer.backend.database.dao

import dev.sergiobelda.todometer.backend.database.entity.NewProjectEntity
import dev.sergiobelda.todometer.backend.database.entity.ProjectEntity
import dev.sergiobelda.todometer.backend.database.mapper.toProjectEntity
import dev.sergiobelda.todometer.backend.database.mapper.toProjectEntityList
import dev.sergiobelda.todometer.backend.database.table.ProjectTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class ProjectDao : IProjectDao {

    // Throw java.util.NoSuchElementException: Collection is empty.
    override suspend fun getProject(id: UUID): ProjectEntity = newSuspendedTransaction {
        ProjectTable.select { ProjectTable.id eq id }.single().toProjectEntity()
    }

    override suspend fun getProjects(): List<ProjectEntity> = newSuspendedTransaction {
        ProjectTable.selectAll().toProjectEntityList()
    }

    override suspend fun insertProject(project: NewProjectEntity): UUID =
        newSuspendedTransaction {
            ProjectTable.replace {
                project.id?.let { uuid ->
                    it[id] = uuid
                }
                it[name] = project.name
                it[description] = project.description
            } get ProjectTable.id
        }

    override suspend fun updateProject(project: ProjectEntity) {
        newSuspendedTransaction {
            ProjectTable.update({ ProjectTable.id eq project.id }) {
                it[name] = project.name
                it[description] = project.description
            }
        }
    }

    override suspend fun deleteProject(id: UUID) {
        newSuspendedTransaction {
            ProjectTable.deleteWhere { ProjectTable.id eq id }
        }
    }
}
