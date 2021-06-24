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

package com.sergiobelda.todometer.common.webservice.client

import com.sergiobelda.todometer.common.webservice.TodometerApi
import com.sergiobelda.todometer.common.webservice.TodometerApi.Companion.ENDPOINT_URL
import com.sergiobelda.todometer.common.webservice.TodometerApi.Companion.PROJECT_PATH
import com.sergiobelda.todometer.common.webservice.TodometerApi.Companion.VERSION_1
import com.sergiobelda.todometer.common.webservice.model.ProjectApiModel
import com.sergiobelda.todometer.common.webservice.request.NewProjectRequestBody
import com.sergiobelda.todometer.common.webservice.request.UpdateProjectRequestBody
import io.ktor.client.request.*
import io.ktor.http.*

class ProjectApiClient(private val todometerApi: TodometerApi) : IProjectApiClient {

    override suspend fun getProjects(): Array<ProjectApiModel> =
        todometerApi.client.get(ENDPOINT_URL + VERSION_1 + PROJECT_PATH)

    override suspend fun getProject(id: String): ProjectApiModel =
        todometerApi.client.get(
            ENDPOINT_URL + VERSION_1 + PROJECT_PATH
        ) {
            parametersOf("id", id)
        }

    override suspend fun insertProject(id: String?, name: String, description: String): String =
        todometerApi.client.post(ENDPOINT_URL + VERSION_1 + PROJECT_PATH) {
            contentType(ContentType.Application.Json)
            body = NewProjectRequestBody(id, name, description)
        }

    override suspend fun updateProject(id: String, name: String, description: String) {
        todometerApi.client.put<Unit>(ENDPOINT_URL + VERSION_1 + PROJECT_PATH) {
            contentType(ContentType.Application.Json)
            body = UpdateProjectRequestBody(id, name, description)
        }
    }

    override suspend fun deleteProject(id: String) =
        todometerApi.client.delete<Unit>(ENDPOINT_URL + VERSION_1 + PROJECT_PATH) {
            parametersOf("id", id)
        }
}
