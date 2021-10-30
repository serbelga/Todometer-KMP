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

package dev.sergiobelda.todometer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.data.Result
import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.preferences.AppTheme
import dev.sergiobelda.todometer.common.usecase.DeleteProjectUseCase
import dev.sergiobelda.todometer.common.usecase.DeleteTaskUseCase
import dev.sergiobelda.todometer.common.usecase.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.GetProjectsUseCase
import dev.sergiobelda.todometer.common.usecase.GetTasksUseCase
import dev.sergiobelda.todometer.common.usecase.RefreshProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.RefreshProjectsUseCase
import dev.sergiobelda.todometer.common.usecase.SetAppThemeUseCase
import dev.sergiobelda.todometer.common.usecase.SetProjectSelectedUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val setTaskDoingUseCase: SetTaskDoingUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
    private val setProjectSelectedUseCase: SetProjectSelectedUseCase,
    private val refreshProjectsUseCase: RefreshProjectsUseCase,
    private val refreshProjectSelectedUseCase: RefreshProjectSelectedUseCase,
    getProjectSelectedUseCase: GetProjectSelectedUseCase,
    getProjectsUseCase: GetProjectsUseCase,
    getTasksUseCase: GetTasksUseCase,
    getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase
) : ViewModel() {

    val appTheme: StateFlow<AppTheme> =
        getAppThemeUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            AppTheme.FOLLOW_SYSTEM
        )

    val tasks: StateFlow<Result<List<Task>>> =
        getTasksUseCase().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Result.Loading)

    val projects: StateFlow<Result<List<Project>>> =
        getProjectsUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading
        )

    val projectSelected: StateFlow<Result<Project?>> =
        getProjectSelectedUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading
        )

    init {
        viewModelScope.launch {
            // refreshProjectsUseCase()
            // refreshProjectSelectedUseCase()
        }
    }

    fun deleteTask(id: String) = viewModelScope.launch {
        deleteTaskUseCase(id)
    }

    fun deleteProject() = viewModelScope.launch {
        deleteProjectUseCase()
    }

    fun setTaskDoing(id: String) = viewModelScope.launch {
        setTaskDoingUseCase(id)
    }

    fun setTaskDone(id: String) = viewModelScope.launch {
        setTaskDoneUseCase(id)
    }

    fun setProjectSelected(id: String) = viewModelScope.launch {
        setProjectSelectedUseCase(id)
    }

    fun setAppTheme(theme: AppTheme) = viewModelScope.launch {
        setAppThemeUseCase(theme)
    }
}
