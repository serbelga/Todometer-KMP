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

package com.sergiobelda.todometer.common.api.client

import com.sergiobelda.todometer.common.api.TodometerApi
import com.sergiobelda.todometer.common.api.TodometerApi.Companion.ENDPOINT_URL
import com.sergiobelda.todometer.common.api.TodometerApi.Companion.TASK_PATH
import com.sergiobelda.todometer.common.api.TodometerApi.Companion.VERSION_1
import com.sergiobelda.todometer.common.api.model.TaskApiModel
import com.sergiobelda.todometer.common.api.model.TaskTagApiModel
import com.sergiobelda.todometer.common.api.request.NewTaskRequestBody
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.parametersOf

class TaskApiClient(private val todometerApi: TodometerApi) : ITaskApiClient {

    override suspend fun getTasks(): List<TaskTagApiModel> =
        todometerApi.client.get(ENDPOINT_URL + VERSION_1 + TASK_PATH)

    override suspend fun getTasksByProjectId(id: String): List<TaskTagApiModel> =
        todometerApi.client.get(
            ENDPOINT_URL + VERSION_1 + TASK_PATH
        ) {
            parametersOf("projectId", id)
        }

    override suspend fun getTask(id: String): TaskApiModel =
        todometerApi.client.get(
            ENDPOINT_URL + VERSION_1 + TASK_PATH
        ) {
            parametersOf("id", id)
        }

    override suspend fun insertTask(
        id: String?,
        title: String,
        description: String,
        state: String,
        projectId: String,
        tagId: String?
    ): String =
        todometerApi.client.post(ENDPOINT_URL + VERSION_1 + TASK_PATH) {
            contentType(ContentType.Application.Json)
            body = NewTaskRequestBody(id, title, description, state, projectId, tagId)
        }

    override suspend fun deleteTask(id: String) =
        todometerApi.client.delete<Unit>(ENDPOINT_URL + VERSION_1 + TASK_PATH) {
            parametersOf("id", id)
        }
}
