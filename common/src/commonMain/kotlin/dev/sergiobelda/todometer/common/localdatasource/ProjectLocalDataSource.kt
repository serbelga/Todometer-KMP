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

package dev.sergiobelda.todometer.common.localdatasource

import dev.sergiobelda.todometer.common.data.Result
import dev.sergiobelda.todometer.common.database.dao.IProjectDao
import dev.sergiobelda.todometer.common.database.mapper.toDomain
import dev.sergiobelda.todometer.common.database.mapper.toEntity
import dev.sergiobelda.todometer.common.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// TODO Catch errors and return Result.Error
class ProjectLocalDataSource(
    private val projectDao: IProjectDao
) : IProjectLocalDataSource {

    override fun getProjects(): Flow<Result<List<Project>>> =
        projectDao.getProjects().map { list ->
            Result.Success(list.toDomain())
        }

    override fun getProject(id: String): Flow<Result<Project>> =
        projectDao.getProject(id).map { projectTaskRelation ->
            projectTaskRelation?.let { Result.Success(it.toDomain()) } ?: Result.Error()
        }

    override suspend fun insertProject(project: Project): Result<String> {
        val projectId = projectDao.insertProject(project.toEntity())
        return Result.Success(projectId)
    }

    override suspend fun insertProjects(projects: List<Project>) =
        projectDao.insertProjects(projects.map { it.toEntity() })

    override suspend fun updateProject(project: Project) =
        projectDao.updateProject(project.toEntity())

    override suspend fun deleteProject(id: String) = projectDao.deleteProject(id)
}
