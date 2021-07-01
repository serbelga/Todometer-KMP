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

package com.sergiobelda.backend.database.dao

import com.sergiobelda.backend.database.entity.NewTaskEntity
import com.sergiobelda.backend.database.entity.TaskTagView
import com.sergiobelda.backend.database.mapper.toTaskTagView
import com.sergiobelda.backend.database.mapper.toTaskTagViewList
import com.sergiobelda.backend.database.table.TaskTable
import com.sergiobelda.backend.database.taskTagViewTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class TaskDao : ITaskDao {

    override suspend fun getTask(id: UUID): TaskTagView = newSuspendedTransaction {
        taskTagViewTable.select { TaskTable.id eq id }.single().toTaskTagView()
    }

    override suspend fun getTasks(projectId: UUID?): List<TaskTagView> = newSuspendedTransaction {
        if (projectId != null) {
            taskTagViewTable.select { TaskTable.projectId eq projectId }.toTaskTagViewList()
        } else {
            taskTagViewTable.selectAll().toTaskTagViewList()
        }
    }

    override suspend fun insertTask(task: NewTaskEntity): UUID =
        newSuspendedTransaction {
            TaskTable.replace {
                task.id?.let { uuid ->
                    it[id] = uuid
                }
                it[title] = task.title
                it[description] = task.description
                it[state] = task.state
                it[projectId] = task.projectId
                it[tagId] = task.tagId
            }
        } get TaskTable.id

    override suspend fun deleteTask(id: UUID) {
        newSuspendedTransaction {
            TaskTable.deleteWhere { TaskTable.id eq id }
        }
    }
}
