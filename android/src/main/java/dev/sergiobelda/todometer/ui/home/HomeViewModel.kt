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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.usecase.DeleteTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.DeleteTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.RefreshTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.RefreshTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.SetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.preferences.AppTheme
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val setTaskDoingUseCase: SetTaskDoingUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteTaskListSelectedUseCase: DeleteTaskListSelectedUseCase,
    private val setTaskListSelectedUseCase: SetTaskListSelectedUseCase,
    private val refreshTaskListsUseCase: RefreshTaskListsUseCase,
    private val refreshTaskListSelectedUseCase: RefreshTaskListSelectedUseCase,
    private val getTaskListSelectedUseCase: GetTaskListSelectedUseCase,
    private val getTaskListsUseCase: GetTaskListsUseCase,
    private val getTaskListSelectedTasksUseCase: GetTaskListSelectedTasksUseCase,
    getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase
) : ViewModel() {

    val appTheme: StateFlow<AppTheme> =
        getAppThemeUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            AppTheme.FOLLOW_SYSTEM
        )

    var homeUiState by mutableStateOf(HomeUiState(isLoadingTasks = true))
        private set

    init {
        getTaskListSelected()
        getTaskListSelectedTasks()
        getTaskLists()

        viewModelScope.launch {
            // refreshTaskListsUseCase()
            // refreshTaskListSelectedUseCase()
        }
    }

    private fun getTaskListSelected() = viewModelScope.launch {
        getTaskListSelectedUseCase().collect { result ->
            result.doIfSuccess { taskList ->
                homeUiState = homeUiState.copy(
                    taskListSelected = taskList,
                    isDefaultTaskListSelected = false
                )
            }.doIfError {
                homeUiState = homeUiState.copy(
                    taskListSelected = null,
                    isDefaultTaskListSelected = true,
                )
            }
        }
    }

    private fun getTaskListSelectedTasks() = viewModelScope.launch {
        getTaskListSelectedTasksUseCase().collect { result ->
            result.doIfSuccess { tasks ->
                homeUiState = homeUiState.copy(
                    isLoadingTasks = false,
                    tasks = tasks
                )
            }.doIfError {
                homeUiState = homeUiState.copy(
                    isLoadingTasks = false,
                    tasks = emptyList()
                )
            }
        }
    }

    private fun getTaskLists() = viewModelScope.launch {
        getTaskListsUseCase().collect { result ->
            result.doIfSuccess { taskLists ->
                homeUiState = homeUiState.copy(
                    taskLists = taskLists
                )
            }.doIfError {
                homeUiState = homeUiState.copy(
                    taskLists = emptyList()
                )
            }
        }
    }

    fun deleteTask(id: String) = viewModelScope.launch {
        deleteTaskUseCase(id)
    }

    fun deleteTaskList() = viewModelScope.launch {
        deleteTaskListSelectedUseCase()
    }

    fun setTaskDoing(id: String) = viewModelScope.launch {
        setTaskDoingUseCase(id)
    }

    fun setTaskDone(id: String) = viewModelScope.launch {
        setTaskDoneUseCase(id)
    }

    fun setTaskListSelected(id: String) = viewModelScope.launch {
        setTaskListSelectedUseCase(id)
    }

    fun setAppTheme(theme: AppTheme) = viewModelScope.launch {
        setAppThemeUseCase(theme)
    }
}
