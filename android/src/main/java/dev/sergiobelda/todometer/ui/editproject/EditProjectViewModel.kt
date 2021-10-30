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

package dev.sergiobelda.todometer.ui.editproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.data.Result
import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.UpdateProjectUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditProjectViewModel(
    getProjectSelectedUseCase: GetProjectSelectedUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase
) : ViewModel() {

    val projectSelected: StateFlow<Result<Project>> =
        getProjectSelectedUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading
        )

    fun updateProject(project: Project) = viewModelScope.launch {
        updateProjectUseCase(project)
    }
}
