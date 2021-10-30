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

package dev.sergiobelda.todometer.common.repository

import dev.sergiobelda.todometer.common.data.Result
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.localdatasource.IProjectLocalDataSource
import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.remotedatasource.IProjectRemoteDataSource
import dev.sergiobelda.todometer.common.util.randomUUIDString
import kotlinx.coroutines.flow.Flow

/**
 * Repository for performing [Project] data operations.
 */
class ProjectRepository(
    private val projectLocalDataSource: IProjectLocalDataSource,
    private val projectRemoteDataSource: IProjectRemoteDataSource
) : IProjectRepository {

    override fun getProject(id: String): Flow<Result<Project>> =
        projectLocalDataSource.getProject(id)

    override fun getProjects(): Flow<Result<List<Project>>> =
        projectLocalDataSource.getProjects()
    /*
    projectLocalDataSource.getProjects().map { result ->
        result.doIfSuccess { projects ->
            synchronizeProjectsRemotely(projects.filter { !it.sync })
            refreshProjects()
        }
    }
    */

    /*
    private suspend fun synchronizeProjectsRemotely(projects: List<Project>) {
        projects.forEach { project ->
            val result = projectRemoteDataSource.insertProject(
                id = project.id,
                name = project.name,
                description = project.description
            )
            result.doIfSuccess {
                projectLocalDataSource.updateProject(
                    project.copy(sync = true)
                )
            }
        }
    }
    */

    override suspend fun refreshProject(id: String) {
        val projectResult = projectRemoteDataSource.getProject(id)
        projectResult.doIfSuccess { project ->
            projectLocalDataSource.insertProject(project)
        }
    }

    override suspend fun refreshProjects() {
        val projectsResult = projectRemoteDataSource.getProjects()
        projectsResult.doIfSuccess {
            projectLocalDataSource.insertProjects(it)
        }
    }

    override suspend fun insertProject(name: String): Result<String> {
        val projectId = randomUUIDString()
        val sync = false
        /*
        projectRemoteDataSource.insertProject(name = name, description = "").doIfSuccess {
            projectId = it
            sync = true
        }.doIfError {
            projectId = randomUUIDString()
        }
        */
        return projectLocalDataSource.insertProject(
            Project(
                id = projectId,
                name = name,
                description = "",
                sync = sync
            )
        )
    }

    override suspend fun updateProject(project: Project) =
        projectLocalDataSource.updateProject(project)

    override suspend fun deleteProject(id: String) = projectLocalDataSource.deleteProject(id)
    /*
    projectRemoteDataSource.deleteProject(id).doIfSuccess {
        projectLocalDataSource.deleteProject(id)
    }
    */
}
