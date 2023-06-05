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

package dev.sergiobelda.todometer.desktop.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.sergiobelda.todometer.common.compose.ui.home.HomeUiState
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.task.DeleteTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.DeleteTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.SetTaskListSelectedUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeViewModel(
    private val coroutineScope: CoroutineScope,
    private val setTaskDoingUseCase: SetTaskDoingUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
    private val deleteTaskListSelectedUseCase: DeleteTaskListSelectedUseCase,
    private val setTaskListSelectedUseCase: SetTaskListSelectedUseCase,
    private val getTaskListSelectedUseCase: GetTaskListSelectedUseCase,
    private val getTaskListsUseCase: GetTaskListsUseCase,
    private val getTaskListSelectedTasksUseCase: GetTaskListSelectedTasksUseCase
) {

    var homeUiState by mutableStateOf(HomeUiState(isLoadingTasks = true))
        private set

    init {
        getTaskListSelected()
        getTaskListSelectedTasks()
        getTaskLists()
    }

    private fun getTaskListSelected() = coroutineScope.launch {
        getTaskListSelectedUseCase().collect { result ->
            result.doIfSuccess { taskList ->
                homeUiState = homeUiState.copy(
                    taskListSelected = taskList
                )
            }.doIfError {
                homeUiState = homeUiState.copy(
                    taskListSelected = null
                )
            }
        }
    }

    private fun getTaskListSelectedTasks() = coroutineScope.launch {
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

    private fun getTaskLists() = coroutineScope.launch {
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

    fun deleteSelectedTasks() = coroutineScope.launch {
        deleteTasksUseCase(homeUiState.selectedTasks)
    }

    fun deleteTask(id: String) = coroutineScope.launch {
        deleteTasksUseCase(id)
    }

    fun deleteTaskList() = coroutineScope.launch {
        deleteTaskListSelectedUseCase()
    }

    fun setTaskDoing(id: String) = coroutineScope.launch {
        setTaskDoingUseCase(id)
    }

    fun setTaskDone(id: String) = coroutineScope.launch {
        setTaskDoneUseCase(id)
    }

    fun setTaskListSelected(id: String) = coroutineScope.launch {
        setTaskListSelectedUseCase(id)
    }

    fun toggleSelectTask(id: String) {
        val selectedTasks = homeUiState.selectedTasks.toMutableList()
        if (!selectedTasks.contains(id)) {
            selectedTasks.add(id)
        } else {
            selectedTasks.removeAll { it == id }
        }
        homeUiState = homeUiState.copy(
            selectedTasks = selectedTasks
        )
    }

    fun clearSelectedTasks() {
        homeUiState = homeUiState.copy(selectedTasks = emptyList())
    }
}
