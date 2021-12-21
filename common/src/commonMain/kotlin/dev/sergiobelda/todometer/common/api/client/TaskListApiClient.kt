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

package dev.sergiobelda.todometer.common.api.client

import dev.sergiobelda.todometer.common.api.TodometerApi
import dev.sergiobelda.todometer.common.api.TodometerApi.Companion.ENDPOINT_URL
import dev.sergiobelda.todometer.common.api.TodometerApi.Companion.TASK_LIST_PATH
import dev.sergiobelda.todometer.common.api.TodometerApi.Companion.VERSION_1
import dev.sergiobelda.todometer.common.api.model.TaskListApiModel
import dev.sergiobelda.todometer.common.api.request.NewTaskListRequestBody
import dev.sergiobelda.todometer.common.api.request.UpdateTaskListRequestBody
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parametersOf

class TaskListApiClient(private val todometerApi: TodometerApi) : ITaskListApiClient {

    override suspend fun getTaskLists(): List<TaskListApiModel> =
        todometerApi.client.get(ENDPOINT_URL + VERSION_1 + TASK_LIST_PATH)

    override suspend fun getTaskList(id: String): TaskListApiModel =
        todometerApi.client.get(
            ENDPOINT_URL + VERSION_1 + TASK_LIST_PATH
        ) {
            parametersOf("id", id)
        }

    override suspend fun insertTaskList(newTaskListRequestBody: NewTaskListRequestBody): String =
        todometerApi.client.post(ENDPOINT_URL + VERSION_1 + TASK_LIST_PATH) {
            contentType(ContentType.Application.Json)
            body = newTaskListRequestBody
        }

    override suspend fun updateTaskList(id: String, name: String, description: String) {
        todometerApi.client.put<Unit>(ENDPOINT_URL + VERSION_1 + TASK_LIST_PATH) {
            contentType(ContentType.Application.Json)
            body = UpdateTaskListRequestBody(id, name, description)
        }
    }

    override suspend fun deleteTaskList(id: String): String =
        todometerApi.client.delete("$ENDPOINT_URL$VERSION_1$TASK_LIST_PATH/$id")
}
