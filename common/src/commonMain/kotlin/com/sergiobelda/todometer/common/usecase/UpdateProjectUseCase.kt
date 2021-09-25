package com.sergiobelda.todometer.common.usecase

import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.repository.IProjectRepository

class UpdateProjectUseCase(private val projectRepository: IProjectRepository) {

    /**
     * Updates a [Project] given a [project] object.
     */
    suspend operator fun invoke(project: Project) =
        projectRepository.updateProject(project)
}
