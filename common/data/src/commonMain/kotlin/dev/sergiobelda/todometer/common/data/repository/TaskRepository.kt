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

package dev.sergiobelda.todometer.common.data.repository

import dev.sergiobelda.todometer.common.data.localdatasource.ITaskLocalDataSource
import dev.sergiobelda.todometer.common.data.util.randomUUIDString
import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.domain.repository.ITaskRepository
import kotlinx.coroutines.flow.Flow

/**
 * Repository for performing [Task] data operations.
 */
class TaskRepository(private val taskLocalDataSource: ITaskLocalDataSource) : ITaskRepository {

    override fun getTask(id: String): Flow<Result<Task>> =
        taskLocalDataSource.getTask(id)

    override fun getTasks(taskListId: String): Flow<Result<List<TaskItem>>> =
        taskLocalDataSource.getTasks(taskListId)

    override suspend fun insertTask(
        title: String,
        tag: Tag,
        description: String?,
        dueDate: Long?,
        taskListId: String
    ): Result<String> {
        val taskId = randomUUIDString()
        val sync = false
        return taskLocalDataSource.insertTask(
            Task(
                id = taskId,
                title = title,
                tag = tag,
                description = description,
                dueDate = dueDate,
                state = TaskState.DOING,
                taskListId = taskListId,
                isPinned = false,
                sync = sync
            )
        )
    }

    override suspend fun updateTask(task: Task) = taskLocalDataSource.updateTask(task)

    override suspend fun updateTaskState(id: String, state: TaskState) {
        taskLocalDataSource.updateTaskState(id, state)
    }

    override suspend fun deleteTasks(vararg ids: String) =
        taskLocalDataSource.deleteTasks(ids = ids)

    override suspend fun toggleTaskPinnedValue(id: String) {
        taskLocalDataSource.toggleTaskPinnedValue(id)
    }
}
