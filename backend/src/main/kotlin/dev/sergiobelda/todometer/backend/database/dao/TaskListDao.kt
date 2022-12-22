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

import dev.sergiobelda.todometer.backend.database.entity.NewTaskListEntity
import dev.sergiobelda.todometer.backend.database.entity.TaskListEntity
import dev.sergiobelda.todometer.backend.database.mapper.toTaskListEntity
import dev.sergiobelda.todometer.backend.database.mapper.toTaskListEntityList
import dev.sergiobelda.todometer.backend.database.table.TaskListTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

class TaskListDao : ITaskListDao {

    // Throw java.util.NoSuchElementException: Collection is empty.
    override suspend fun getTaskList(id: UUID): TaskListEntity = newSuspendedTransaction {
        TaskListTable.select { TaskListTable.id eq id }.single().toTaskListEntity()
    }

    override suspend fun getTaskLists(): List<TaskListEntity> = newSuspendedTransaction {
        TaskListTable.selectAll().toTaskListEntityList()
    }

    override suspend fun insertTaskList(taskList: NewTaskListEntity): UUID =
        newSuspendedTransaction {
            TaskListTable.replace {
                taskList.id?.let { uuid ->
                    it[id] = uuid
                }
                it[name] = taskList.name
                it[description] = taskList.description
            } get TaskListTable.id
        }

    override suspend fun updateTaskList(taskList: TaskListEntity) {
        newSuspendedTransaction {
            TaskListTable.update({ TaskListTable.id eq taskList.id }) {
                it[name] = taskList.name
                it[description] = taskList.description
            }
        }
    }

    override suspend fun deleteTaskList(id: UUID) {
        newSuspendedTransaction {
            TaskListTable.deleteWhere { TaskListTable.id eq id }
        }
    }
}
