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

package dev.sergiobelda.todometer.common.data.localdatasource

import dev.sergiobelda.todometer.common.database.dao.ITaskDao
import dev.sergiobelda.todometer.common.database.mapper.asTask
import dev.sergiobelda.todometer.common.database.mapper.asTaskEntity
import dev.sergiobelda.todometer.common.database.mapper.asTaskItems
import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskLocalDataSource(
    private val taskDao: ITaskDao
) : ITaskLocalDataSource {

    override fun getTask(id: String): Flow<Result<Task>> =
        taskDao.getTask(id).map { taskEntity ->
            taskEntity?.let { Result.Success(it.asTask()) } ?: Result.Error()
        }

    override fun getTasks(taskListId: String): Flow<Result<List<TaskItem>>> =
        taskDao.getTasks(taskListId).map { list ->
            Result.Success(list.asTaskItems())
        }

    override suspend fun insertTask(task: Task): Result<String> {
        val taskId = taskDao.insertTask(task.asTaskEntity())
        return Result.Success(taskId)
    }

    override suspend fun insertTasks(tasks: List<Task>) {
        taskDao.insertTasks(tasks.map { it.asTaskEntity() })
    }

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task.asTaskEntity())

    override suspend fun updateTaskSync(id: String, sync: Boolean) =
        taskDao.updateTaskSync(id, sync)

    override suspend fun updateTaskState(id: String, state: TaskState) =
        taskDao.updateTaskState(id, state)

    override suspend fun deleteTask(id: String) =
        taskDao.deleteTask(id)
}
