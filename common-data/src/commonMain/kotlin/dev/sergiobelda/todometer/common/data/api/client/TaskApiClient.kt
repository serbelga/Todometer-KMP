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

package dev.sergiobelda.todometer.common.data.api.client

import dev.sergiobelda.todometer.common.data.api.TodometerApi
import dev.sergiobelda.todometer.common.data.api.TodometerApi.Companion.ENDPOINT_URL
import dev.sergiobelda.todometer.common.data.api.TodometerApi.Companion.TASK_PATH
import dev.sergiobelda.todometer.common.data.api.TodometerApi.Companion.VERSION_1
import dev.sergiobelda.todometer.common.data.api.model.TaskApiModel
import dev.sergiobelda.todometer.common.data.api.request.NewTaskRequestBody
import dev.sergiobelda.todometer.common.data.api.request.UpdateTaskStateRequestBody
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parametersOf

class TaskApiClient(private val todometerApi: TodometerApi) : ITaskApiClient {

    override suspend fun getTasks(): List<TaskApiModel> =
        todometerApi.client.get(ENDPOINT_URL + VERSION_1 + TASK_PATH)

    override suspend fun getTasks(taskListId: String?): List<TaskApiModel> =
        todometerApi.client.get(
            ENDPOINT_URL + VERSION_1 + TASK_PATH
        ) {
            taskListId?.let { parametersOf(TASK_LIST_ID_PARAM, it) }
        }

    override suspend fun getTask(id: String): TaskApiModel =
        todometerApi.client.get(
            ENDPOINT_URL + VERSION_1 + TASK_PATH
        ) {
            parametersOf("id", id)
        }

    override suspend fun insertTask(newTaskRequestBody: NewTaskRequestBody): String =
        todometerApi.client.post(ENDPOINT_URL + VERSION_1 + TASK_PATH) {
            contentType(ContentType.Application.Json)
            body = newTaskRequestBody
        }

    override suspend fun deleteTask(id: String): String =
        todometerApi.client.delete("$ENDPOINT_URL$VERSION_1$TASK_PATH/$id")

    override suspend fun updateTaskState(
        id: String,
        updateTaskRequestBody: UpdateTaskStateRequestBody
    ): String = todometerApi.client.put("$ENDPOINT_URL$VERSION_1$TASK_PATH/$id/$STATE_PATH") {
        contentType(ContentType.Application.Json)
        body = updateTaskRequestBody
    }

    companion object {
        private const val STATE_PATH = "state"
        private const val TASK_LIST_ID_PARAM = "taskListId"
    }
}
