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
import com.sergiobelda.todometer.common.webservice.model.ProjectApiModel
import com.sergiobelda.todometer.common.webservice.request.ProjectRequestBody
import io.ktor.client.request.get
import io.ktor.client.request.post

class ProjectApiClient(private val todometerApi: TodometerApi) : IProjectApiClient {

    override suspend fun getProjects(): List<ProjectApiModel> = todometerApi.client.get(TodometerApi.ENDPOINT_URL + TodometerApi.PROJECT_PATH)

    override suspend fun insertProject(name: String, description: String): Long = todometerApi.client.post(TodometerApi.ENDPOINT_URL + TodometerApi.PROJECT_PATH) {
        body = ProjectRequestBody(name, description)
    }
}
