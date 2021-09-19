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

package com.sergiobelda.todometer.common.usecase

import com.sergiobelda.todometer.common.repository.IProjectRepository
import com.sergiobelda.todometer.common.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteProjectUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val projectRepository: IProjectRepository
) {

    /**
     * Deletes a project. The deleted project will be the current project selected.
     * Once is deleted, it will select the first project in projects list if is not empty.
     */
    suspend operator fun invoke() {
        val projectId = userPreferencesRepository.projectSelected().firstOrNull()
        projectId?.let { projectRepository.deleteProject(it) }

    }
}
