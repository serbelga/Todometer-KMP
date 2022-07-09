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

package dev.sergiobelda.todometer.common.data.remotedatasource

import dev.sergiobelda.todometer.common.data.api.client.ITaskListApiClient
import dev.sergiobelda.todometer.common.data.api.mapper.toTaskList
import dev.sergiobelda.todometer.common.data.api.mapper.toTaskLists
import dev.sergiobelda.todometer.common.data.api.request.NewTaskListRequestBody
import dev.sergiobelda.todometer.common.data.api.safeApiCall
import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.TaskList

class TaskListRemoteDataSource(private val taskListApiClient: ITaskListApiClient) :
    ITaskListRemoteDataSource {

    override suspend fun getTaskLists(): Result<List<TaskList>> =
        safeApiCall {
            taskListApiClient.getTaskLists()
        }.map { it.toTaskLists() }

    override suspend fun getTaskList(id: String): Result<TaskList> =
        safeApiCall {
            taskListApiClient.getTaskList(id)
        }.map { it.toTaskList() }

    override suspend fun insertTaskList(
        id: String?,
        name: String,
        description: String
    ): Result<String> =
        safeApiCall {
            taskListApiClient.insertTaskList(NewTaskListRequestBody(id, name, description))
        }

    override suspend fun updateTaskList(
        id: String,
        name: String,
        description: String
    ): Result<Unit> = safeApiCall {
        taskListApiClient.updateTaskList(id, name, description)
    }

    override suspend fun deleteTaskList(id: String): Result<String> = safeApiCall {
        taskListApiClient.deleteTaskList(id)
    }
}
