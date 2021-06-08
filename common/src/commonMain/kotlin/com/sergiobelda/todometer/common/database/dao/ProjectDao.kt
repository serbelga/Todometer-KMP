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

package com.sergiobelda.todometer.common.database.dao

import com.sergiobelda.todometer.ProjectEntity
import com.sergiobelda.todometer.TodometerDatabase
import com.sergiobelda.todometer.common.database.ProjectTasksRelation
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProjectDao : IProjectDao, KoinComponent {

    private val database: TodometerDatabase by inject()

    override fun getProjects(): Flow<List<ProjectEntity>> =
        database.todometerQueries.selectAllProjects().asFlow().mapToList()

    override fun getProject(id: String): Flow<ProjectTasksRelation?> =
        database.todometerQueries.selectProject(id).asFlow().mapToOneOrNull().combine(
            database.todometerQueries.selectTasksByProjectId(id).asFlow().mapToList()
        ) { project, tasks ->
            project?.let {
                ProjectTasksRelation(
                    it,
                    tasks
                )
            }
        }

    override suspend fun insertProject(project: ProjectEntity) {
        database.todometerQueries.insertOrReplaceProject(
            id = project.id,
            name = project.name,
            description = project.description,
            sync = project.sync
        )
    }

    override suspend fun insertProjects(projects: List<ProjectEntity>) {
        projects.forEach { project ->
            database.todometerQueries.insertOrReplaceProject(
                id = project.id,
                name = project.name,
                description = project.description,
                sync = project.sync
            )
        }
    }

    override suspend fun updateProject(project: ProjectEntity) {
        database.todometerQueries.updateProject(
            id = project.id,
            name = project.name,
            description = project.description,
            sync = project.sync
        )
    }

    override suspend fun updateProjects(projects: List<ProjectEntity>) {
        projects.forEach { project ->
            database.todometerQueries.updateProject(
                id = project.id,
                name = project.name,
                description = project.description,
                sync = project.sync
            )
        }
    }
}
