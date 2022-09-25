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

import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.network.client.ITaskApiClient
import dev.sergiobelda.todometer.common.network.mapper.asTaskTagList
import dev.sergiobelda.todometer.common.network.request.NewTaskRequestBody
import dev.sergiobelda.todometer.common.network.request.UpdateTaskStateRequestBody
import dev.sergiobelda.todometer.common.network.safeApiCall

class TaskRemoteDataSource(private val taskApiClient: ITaskApiClient) : ITaskRemoteDataSource {

    override suspend fun getTasks(taskListId: String?): Result<List<Task>> =
        safeApiCall {
            taskApiClient.getTasks(taskListId)
        }.map { it.asTaskTagList() }

    override suspend fun insertTask(
        id: String?,
        title: String,
        description: String?,
        state: TaskState,
        taskListId: String,
        tag: Tag
    ): Result<String> = safeApiCall {
        taskApiClient.insertTask(
            NewTaskRequestBody(
                id,
                title,
                description ?: "",
                state.name,
                taskListId,
                tag.toString()
            )
        )
    }

    override suspend fun updateTask() {
    }

    override suspend fun updateTaskState(id: String, state: TaskState): Result<String> =
        safeApiCall {
            taskApiClient.updateTaskState(id, UpdateTaskStateRequestBody(state.name))
        }

    override suspend fun deleteTask(id: String): Result<String> = safeApiCall {
        taskApiClient.deleteTask(id)
    }
}
