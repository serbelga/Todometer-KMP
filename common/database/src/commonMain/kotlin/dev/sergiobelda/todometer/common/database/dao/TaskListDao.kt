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

package dev.sergiobelda.todometer.common.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import dev.sergiobelda.todometer.common.database.TaskListEntity
import dev.sergiobelda.todometer.common.database.TodometerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TaskListDao(private val todometerDatabase: TodometerDatabase) : ITaskListDao {

    override fun getTaskLists(): Flow<List<TaskListEntity>> =
        todometerDatabase.taskListEntityQueries.selectAllTaskLists().asFlow()
            .mapToList(Dispatchers.Default)

    override fun getTaskList(id: String): Flow<TaskListEntity?> =
        todometerDatabase.taskListEntityQueries.selectTaskList(id).asFlow()
            .mapToOneOrNull(Dispatchers.Default)

    override suspend fun insertTaskList(taskList: TaskListEntity): String {
        todometerDatabase.taskListEntityQueries.insertOrReplaceTaskList(
            id = taskList.id,
            name = taskList.name,
            description = taskList.description,
            sync = taskList.sync,
        )
        // TODO Call return last_insert_rowid() from SQLDelight.
        return taskList.id
    }

    override suspend fun insertTaskLists(taskLists: List<TaskListEntity>) =
        taskLists.forEach { taskList ->
            insertTaskList(taskList)
        }

    override suspend fun updateTaskList(taskList: TaskListEntity) {
        todometerDatabase.taskListEntityQueries.updateTaskList(
            id = taskList.id,
            name = taskList.name,
            description = taskList.description,
            sync = taskList.sync,
        )
    }

    override suspend fun updateTaskListName(id: String, name: String) {
        todometerDatabase.taskListEntityQueries.updateTaskListName(id = id, name = name)
    }

    override suspend fun updateTaskLists(taskLists: List<TaskListEntity>) {
        taskLists.forEach { taskList ->
            todometerDatabase.taskListEntityQueries.updateTaskList(
                id = taskList.id,
                name = taskList.name,
                description = taskList.description,
                sync = taskList.sync,
            )
        }
    }

    override suspend fun deleteTaskList(id: String) {
        todometerDatabase.taskListEntityQueries.deleteTaskList(id)
    }
}
