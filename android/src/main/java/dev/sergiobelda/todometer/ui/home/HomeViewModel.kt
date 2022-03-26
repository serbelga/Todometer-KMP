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
import dev.sergiobelda.todometer.common.domain.Result
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
    getTaskListSelectedUseCase: GetTaskListSelectedUseCase,
    getTaskListsUseCase: GetTaskListsUseCase,
    getTaskListSelectedTasksUseCase: GetTaskListSelectedTasksUseCase,
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
        getTaskListSelectedTasksUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading
        )

    val taskLists: StateFlow<Result<List<TaskList>>> =
        getTaskListsUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading
        )

    val taskListSelected: StateFlow<Result<TaskList>> =
        getTaskListSelectedUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Result.Loading
        )

    init {
        viewModelScope.launch {
            // refreshTaskListsUseCase()
            // refreshTaskListSelectedUseCase()
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
