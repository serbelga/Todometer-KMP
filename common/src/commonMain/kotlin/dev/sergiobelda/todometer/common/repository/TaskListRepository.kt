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

package dev.sergiobelda.todometer.common.repository

import dev.sergiobelda.todometer.common.data.Result
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.localdatasource.ITaskListLocalDataSource
import dev.sergiobelda.todometer.common.model.TaskList
import dev.sergiobelda.todometer.common.remotedatasource.ITaskListRemoteDataSource
import dev.sergiobelda.todometer.common.util.randomUUIDString
import kotlinx.coroutines.flow.Flow

/**
 * Repository for performing [TaskList] data operations.
 */
class TaskListRepository(
    private val taskListLocalDataSource: ITaskListLocalDataSource,
    private val taskListRemoteDataSource: ITaskListRemoteDataSource
) : ITaskListRepository {

    override fun getTaskList(id: String): Flow<Result<TaskList>> =
        taskListLocalDataSource.getTaskList(id)

    override fun getTaskLists(): Flow<Result<List<TaskList>>> =
        taskListLocalDataSource.getTaskLists()

    /*
    taskListLocalDataSource.getTaskLists().map { result ->
        result.doIfSuccess { taskLists ->
            synchronizeTaskListsRemotely(taskLists.filter { !it.sync })
            refreshTaskLists()
        }
    }
    */

    /*
    private suspend fun synchronizeTaskListsRemotely(taskLists: List<TaskList>) {
        taskLists.forEach { taskList ->
            val result = taskListRemoteDataSource.insertTaskList(
                id = taskList.id,
                name = taskList.name,
                description = taskList.description
            )
            result.doIfSuccess {
                taskListLocalDataSource.updateTaskList(
                    taskList.copy(sync = true)
                )
            }
        }
    }
    */

    override suspend fun refreshTaskList(id: String) {
        val taskListResult = taskListRemoteDataSource.getTaskList(id)
        taskListResult.doIfSuccess { taskList ->
            taskListLocalDataSource.insertTaskList(taskList)
        }
    }

    override suspend fun refreshTaskLists() {
        val taskListsResult = taskListRemoteDataSource.getTaskLists()
        taskListsResult.doIfSuccess {
            taskListLocalDataSource.insertTaskLists(it)
        }
    }

    override suspend fun insertTaskList(name: String): Result<String> {
        val taskListId = randomUUIDString()
        val sync = false
        /*
        taskListRemoteDataSource.insertTaskList(name = name, description = "").doIfSuccess {
            taskListId = it
            sync = true
        }.doIfError {
            taskListId = randomUUIDString()
        }
        */
        return taskListLocalDataSource.insertTaskList(
            TaskList(
                id = taskListId,
                name = name,
                description = "",
                sync = sync
            )
        )
    }

    override suspend fun updateTaskList(taskList: TaskList) =
        taskListLocalDataSource.updateTaskList(taskList)

    override suspend fun deleteTaskList(id: String) = taskListLocalDataSource.deleteTaskList(id)
    /*
    taskListRemoteDataSource.deleteTaskList(id).doIfSuccess {
        taskListLocalDataSource.deleteTaskList(id)
    }
    */
}
