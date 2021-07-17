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

package com.sergiobelda.todometer.common.remotedatasource

import com.sergiobelda.todometer.common.api.client.IProjectApiClient
import com.sergiobelda.todometer.common.api.mapper.toDomain
import com.sergiobelda.todometer.common.api.request.NewProjectRequestBody
import com.sergiobelda.todometer.common.api.safeApiCall
import com.sergiobelda.todometer.common.data.Result
import com.sergiobelda.todometer.common.model.Project

class ProjectRemoteDataSource(private val projectApiClient: IProjectApiClient) : IProjectRemoteDataSource {

    override suspend fun getProjects(): Result<List<Project>> =
        safeApiCall {
            projectApiClient.getProjects()
        }.map { it.toDomain() }

    override suspend fun getProject(id: String): Result<Project> =
        safeApiCall {
            projectApiClient.getProject(id)
        }.map { it.toDomain() }

    override suspend fun insertProject(
        id: String?,
        name: String,
        description: String
    ): Result<String> =
        safeApiCall {
            projectApiClient.insertProject(NewProjectRequestBody(id, name, description))
        }

    override suspend fun updateProject(
        id: String,
        name: String,
        description: String
    ): Result<Unit> = safeApiCall {
        projectApiClient.updateProject(id, name, description)
    }

    override suspend fun deleteProject(id: String): Result<String> = safeApiCall {
        projectApiClient.deleteProject(id)
    }
}
