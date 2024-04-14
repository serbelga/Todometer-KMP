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

package dev.sergiobelda.todometer.app.feature.home.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.task.DeleteTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.ToggleTaskPinnedValueUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.DeleteTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.SetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val setTaskDoingUseCase: SetTaskDoingUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
    private val deleteTaskListSelectedUseCase: DeleteTaskListSelectedUseCase,
    private val setTaskListSelectedUseCase: SetTaskListSelectedUseCase,
    private val getTaskListSelectedUseCase: GetTaskListSelectedUseCase,
    private val getTaskListsUseCase: GetTaskListsUseCase,
    private val getTaskListSelectedTasksUseCase: GetTaskListSelectedTasksUseCase,
    private val toggleTaskPinnedValueUseCase: ToggleTaskPinnedValueUseCase
) : ViewModel() {

    var uiState by mutableStateOf(HomeUiState(isLoadingTasks = true))
        private set

    init {
        getTaskListSelected()
        getTaskListSelectedTasks()
        getTaskLists()
    }

    private fun getTaskListSelected() = coroutineScope.launch {
        getTaskListSelectedUseCase().collect { result ->
            result.doIfSuccess { taskList ->
                uiState = uiState.copy(
                    taskListSelected = taskList
                )
            }.doIfError {
                uiState = uiState.copy(
                    taskListSelected = null
                )
            }
        }
    }

    private fun getTaskListSelectedTasks() = coroutineScope.launch {
        getTaskListSelectedTasksUseCase().collect { result ->
            result.doIfSuccess { tasks ->
                uiState = uiState.copy(
                    isLoadingTasks = false,
                    tasks = tasks
                )
            }.doIfError {
                uiState = uiState.copy(
                    isLoadingTasks = false,
                    tasks = emptyList()
                )
            }
        }
    }

    private fun getTaskLists() = coroutineScope.launch {
        getTaskListsUseCase().collect { result ->
            result.doIfSuccess { taskLists ->
                uiState = uiState.copy(
                    taskLists = taskLists
                )
            }.doIfError {
                uiState = uiState.copy(
                    taskLists = emptyList()
                )
            }
        }
    }

    fun deleteSelectedTasks() = coroutineScope.launch {
        deleteTasksUseCase(uiState.selectedTasksIds)
        clearSelectedTasks()
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
        val selectedTasksIds = uiState.selectedTasksIds.toMutableList()
        if (!selectedTasksIds.contains(id)) {
            selectedTasksIds.add(id)
        } else {
            selectedTasksIds.removeAll { it == id }
        }
        uiState = uiState.copy(
            selectedTasksIds = selectedTasksIds
        )
    }

    fun clearSelectedTasks() = coroutineScope.launch {
        delay(CLEAR_SELECTED_TASKS_DELAY_MILLIS)
        uiState = uiState.copy(selectedTasksIds = emptyList())
    }

    fun toggleSelectedTasksPinnedValue() = coroutineScope.launch {
        val notPinnedSelectedTasks = uiState.selectedTasks.filter { !it.isPinned }
        if (notPinnedSelectedTasks.isNotEmpty()) {
            notPinnedSelectedTasks.forEach {
                toggleTaskPinnedValueUseCase(it.id)
            }
        } else {
            uiState.selectedTasks.forEach {
                toggleTaskPinnedValueUseCase(it.id)
            }
        }
        clearSelectedTasks()
    }

    companion object {
        private const val CLEAR_SELECTED_TASKS_DELAY_MILLIS: Long = 150
    }
}
