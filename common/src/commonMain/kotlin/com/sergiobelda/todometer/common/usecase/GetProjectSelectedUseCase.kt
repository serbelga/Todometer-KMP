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

import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.repository.IProjectRepository
import com.sergiobelda.todometer.common.repository.IUserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetProjectSelectedUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val projectRepository: IProjectRepository
) {

    /**
     * Retrieves the current project selected. This flow emits a value of a project
     * every time that current project selected in user preferences changes or
     * has been updated in database.
     *
     * @return A Flow that emits the current project selected.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Project?> =
        userPreferencesRepository.projectSelected().flatMapLatest { projectId ->
            projectRepository.getProject(projectId)
        }
}