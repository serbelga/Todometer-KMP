package com.sergiobelda.todometer.common.datasource

import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.ProjectTasks
import kotlinx.coroutines.flow.Flow

interface IProjectLocalDatabaseSource {

    fun getProjects(): Flow<Result<List<Project>>>

    fun getProject(id: Long): Flow<Result<ProjectTasks?>>

    suspend fun insertProject(project: Project)
}
