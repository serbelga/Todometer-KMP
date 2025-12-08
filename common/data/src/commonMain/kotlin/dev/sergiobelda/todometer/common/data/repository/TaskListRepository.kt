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

import dev.sergiobelda.todometer.common.data.localdatasource.ITaskListLocalDataSource
import dev.sergiobelda.todometer.common.data.util.randomUUIDString
import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.repository.ITaskListRepository
import kotlinx.coroutines.flow.Flow

/**
 * Repository for performing [TaskList] data operations.
 */
class TaskListRepository(
    private val taskListLocalDataSource: ITaskListLocalDataSource,
) : ITaskListRepository {
    override fun getTaskList(id: String): Flow<Result<TaskList>> = taskListLocalDataSource.getTaskList(id)

    override fun getTaskLists(): Flow<Result<List<TaskList>>> = taskListLocalDataSource.getTaskLists()

    override suspend fun insertTaskList(name: String): Result<String> {
        val taskListId = randomUUIDString()
        val sync = false
        return taskListLocalDataSource.insertTaskList(
            TaskList(
                id = taskListId,
                name = name,
                description = "",
                sync = sync,
            ),
        )
    }

    override suspend fun updateTaskList(taskList: TaskList) = taskListLocalDataSource.updateTaskList(taskList)

    override suspend fun updateTaskListName(
        id: String,
        name: String,
    ) = taskListLocalDataSource.updateTaskListName(id, name)

    override suspend fun deleteTaskList(id: String) = taskListLocalDataSource.deleteTaskList(id)
}
