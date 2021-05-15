package com.sergiobelda.todometer.common.datasource

import com.sergiobelda.todometer.common.database.dao.IProjectDao
import com.sergiobelda.todometer.common.database.mapper.ProjectMapper.toDomain
import com.sergiobelda.todometer.common.database.mapper.ProjectMapper.toEntity
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.ProjectTasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProjectLocalDatabaseSource(
    private val projectDao: IProjectDao
) : IProjectLocalDatabaseSource {

    override fun getProjects(): Flow<Result<List<Project>>> =
        projectDao.getProjects().map { list ->
            Result.Success(list.map { it.toDomain() })
        }

    override fun getProject(id: Long): Flow<Result<ProjectTasks?>> =
        projectDao.getProject(id).map { projectTaskRelation ->
            Result.Success(projectTaskRelation?.toDomain())
        }

    override suspend fun insertProject(project: Project) =
        projectDao.insertProject(project.toEntity())
}
