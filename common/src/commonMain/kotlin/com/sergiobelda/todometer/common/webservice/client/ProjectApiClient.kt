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
import com.sergiobelda.todometer.common.webservice.request.ProjectRequestBody
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.parametersOf

class ProjectApiClient(private val todometerApi: TodometerApi) : IProjectApiClient {

    override suspend fun getProjects(): List<ProjectApiModel> =
        todometerApi.client.get(ENDPOINT_URL + VERSION_1 + PROJECT_PATH)

    override suspend fun getProject(id: Long): ProjectApiModel =
        todometerApi.client.get(
            ENDPOINT_URL + VERSION_1 + PROJECT_PATH
        ) {
            parametersOf("id", id.toString())
        }

    override suspend fun insertProject(name: String, description: String) =
        todometerApi.client.post<Unit>(ENDPOINT_URL + VERSION_1 + PROJECT_PATH) {
            body = ProjectRequestBody(name, description)
        }

    override suspend fun deleteProject(id: Long) =
        todometerApi.client.delete<Unit>(ENDPOINT_URL + VERSION_1 + PROJECT_PATH) {
            parametersOf("id", id.toString())
        }
}
