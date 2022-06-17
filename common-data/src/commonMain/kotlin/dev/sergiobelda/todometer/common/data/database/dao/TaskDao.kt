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

package dev.sergiobelda.todometer.common.data.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import dev.sergiobelda.todometer.common.data.database.SelectTasksByTaskListId
import dev.sergiobelda.todometer.common.data.database.TaskEntity
import dev.sergiobelda.todometer.common.data.database.TodometerDatabase
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlinx.coroutines.flow.Flow

class TaskDao(private val database: TodometerDatabase) : ITaskDao {

    override fun getTask(id: String): Flow<TaskEntity?> =
        database.taskEntityQueries.selectTask(id).asFlow().mapToOneOrNull()

    override fun getTasks(taskListId: String): Flow<List<SelectTasksByTaskListId>> =
        database.taskEntityQueries.selectTasksByTaskListId(taskListId).asFlow().mapToList()

    override suspend fun insertTask(task: TaskEntity): String {
        database.taskEntityQueries.insertOrReplaceTask(
            id = task.id,
            title = task.title,
            description = task.description,
            state = task.state,
            tag = task.tag,
            tasklist_id = task.tasklist_id,
            sync = task.sync,
            dueDate = task.dueDate
        )
        // TODO Call return last_insert_rowid() from SQLDelight.
        return task.id
    }

    override suspend fun insertTasks(tasks: List<TaskEntity>) =
        tasks.forEach { task ->
            insertTask(task)
        }

    override suspend fun updateTask(task: TaskEntity) =
        database.taskEntityQueries.updateTask(
            id = task.id,
            title = task.title,
            description = task.description,
            tag = task.tag,
            dueDate = task.dueDate
        )

    override suspend fun updateTaskSync(id: String, sync: Boolean) {
        database.taskEntityQueries.updateTaskSync(
            id = id,
            sync = sync
        )
    }

    override suspend fun updateTaskState(id: String, state: TaskState) {
        database.taskEntityQueries.updateTaskState(
            id = id,
            state = state
        )
    }

    override suspend fun deleteTask(id: String) = database.taskEntityQueries.deleteTask(id)
}
