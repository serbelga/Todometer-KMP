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

package com.sergiobelda.todometer.common.repository

import com.sergiobelda.todometer.common.datasource.Result
import com.sergiobelda.todometer.common.datasource.doIfSuccess
import com.sergiobelda.todometer.common.localdatasource.IProjectLocalDataSource
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.ProjectTasks
import com.sergiobelda.todometer.common.remotedatasource.IProjectRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProjectRepository(
    private val projectLocalDataSource: IProjectLocalDataSource,
    private val projectRemoteDataSource: IProjectRemoteDataSource
) : IProjectRepository {

    override fun getProject(id: Long): Flow<Result<ProjectTasks?>> =
        projectLocalDataSource.getProject(id)

    override fun getProjects(): Flow<Result<List<Project>>> = flow {
        // TODO Update body
        val projects = projectRemoteDataSource.getProjects()
        emit(projects)
    }

    override suspend fun insertProject(name: String, description: String) {
        val result = projectRemoteDataSource.insertProject(name, description)
        var sync = false
        result.doIfSuccess {
            sync = true
        }
        projectLocalDataSource.insertProject(Project(name = name, description = description, sync = sync))
    }
}
