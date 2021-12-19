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

import dev.sergiobelda.todometer.backend.database.entity.NewTaskEntity
import dev.sergiobelda.todometer.backend.database.entity.TaskEntity
import dev.sergiobelda.todometer.backend.database.mapper.toTaskEntity
import dev.sergiobelda.todometer.backend.database.mapper.toTaskEntityList
import dev.sergiobelda.todometer.backend.database.table.TaskTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class TaskDao : ITaskDao {

    override suspend fun getTask(id: UUID): TaskEntity = newSuspendedTransaction {
        TaskTable.select { TaskTable.id eq id }.single().toTaskEntity()
    }

    override suspend fun getTasks(taskListId: UUID?): List<TaskEntity> = newSuspendedTransaction {
        if (taskListId != null) {
            TaskTable.select { TaskTable.taskListId eq taskListId }.toTaskEntityList()
        } else {
            TaskTable.selectAll().toTaskEntityList()
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
                it[taskListId] = task.taskListId
                it[tag] = task.tag
            }
        } get TaskTable.id

    override suspend fun updateTaskState(id: UUID, taskState: String) {
        newSuspendedTransaction {
            TaskTable.update({ TaskTable.id eq id }) {
                it[state] = taskState
            }
        }
    }

    override suspend fun deleteTask(id: UUID) {
        newSuspendedTransaction {
            TaskTable.deleteWhere { TaskTable.id eq id }
        }
    }
}
