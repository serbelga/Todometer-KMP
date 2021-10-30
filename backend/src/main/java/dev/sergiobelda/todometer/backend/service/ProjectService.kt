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

package dev.sergiobelda.todometer.backend.service

import dev.sergiobelda.todometer.backend.database.dao.IProjectDao
import dev.sergiobelda.todometer.backend.model.NewProject
import dev.sergiobelda.todometer.backend.model.Project
import dev.sergiobelda.todometer.backend.model.toNewProjectEntity
import dev.sergiobelda.todometer.backend.model.toProject
import dev.sergiobelda.todometer.backend.model.toProjectEntity
import dev.sergiobelda.todometer.backend.model.toProjectList
import java.util.UUID

class ProjectService(private val projectDao: IProjectDao) : IProjectService {

    override suspend fun getProject(id: String): Project =
        projectDao.getProject(UUID.fromString(id)).toProject()

    override suspend fun getProjects(): List<Project> = projectDao.getProjects().toProjectList()

    override suspend fun insertProject(newProject: NewProject): String =
        projectDao.insertProject(newProject.toNewProjectEntity()).toString()

    override suspend fun updateProject(project: Project) =
        projectDao.updateProject(project.toProjectEntity())

    override suspend fun deleteProject(id: String) = projectDao.deleteProject(UUID.fromString(id))
}
