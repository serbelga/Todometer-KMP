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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("TooManyFunctions")
class HomeViewModel(
    private val setTaskDoingUseCase: SetTaskDoingUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
    private val deleteTaskListSelectedUseCase: DeleteTaskListSelectedUseCase,
    private val setTaskListSelectedUseCase: SetTaskListSelectedUseCase,
    private val getTaskListSelectedUseCase: GetTaskListSelectedUseCase,
    private val getTaskListsUseCase: GetTaskListsUseCase,
    private val getTaskListSelectedTasksUseCase: GetTaskListSelectedTasksUseCase,
    private val toggleTaskPinnedValueUseCase: ToggleTaskPinnedValueUseCase,
) : ViewModel() {

    var state by mutableStateOf(HomeState(isLoadingTasks = true))
        private set

    init {
        getTaskListSelected()
        getTaskListSelectedTasks()
        getTaskLists()
    }

    private fun getTaskListSelected() = viewModelScope.launch {
        getTaskListSelectedUseCase().collect { result ->
            result.doIfSuccess { taskList ->
                state = state.copy(
                    taskListSelected = taskList,
                )
            }.doIfError {
                state = state.copy(
                    taskListSelected = null,
                )
            }
        }
    }

    private fun getTaskListSelectedTasks() = viewModelScope.launch {
        getTaskListSelectedTasksUseCase().collect { result ->
            result.doIfSuccess { tasks ->
                state = state.copy(
                    isLoadingTasks = false,
                    tasks = tasks.toPersistentList(),
                )
            }.doIfError {
                state = state.copy(
                    isLoadingTasks = false,
                    tasks = persistentListOf(),
                )
            }
        }
    }

    private fun getTaskLists() = viewModelScope.launch {
        getTaskListsUseCase().collect { result ->
            result.doIfSuccess { taskLists ->
                state = state.copy(
                    taskLists = taskLists.toPersistentList(),
                )
            }.doIfError {
                state = state.copy(
                    taskLists = persistentListOf(),
                )
            }
        }
    }

    fun deleteSelectedTasks() = viewModelScope.launch {
        deleteTasksUseCase(state.selectedTasksIds)
        clearSelectedTasks()
    }

    fun deleteTask(id: String) = viewModelScope.launch {
        deleteTasksUseCase(id)
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

    fun toggleSelectTask(id: String) {
        val selectedTasksIds = state.selectedTasksIds.toMutableList()
        if (!selectedTasksIds.contains(id)) {
            selectedTasksIds.add(id)
        } else {
            selectedTasksIds.removeAll { it == id }
        }
        state = state.copy(
            selectedTasksIds = selectedTasksIds.toPersistentList(),
        )
    }

    fun clearSelectedTasks() = viewModelScope.launch {
        delay(CLEAR_SELECTED_TASKS_DELAY_MILLIS)
        state = state.copy(selectedTasksIds = persistentListOf())
    }

    fun toggleSelectedTasksPinnedValue() = viewModelScope.launch {
        val notPinnedSelectedTasks = state.selectedTasks.filter { !it.isPinned }
        if (notPinnedSelectedTasks.isNotEmpty()) {
            notPinnedSelectedTasks.forEach {
                toggleTaskPinnedValueUseCase(it.id)
            }
        } else {
            state.selectedTasks.forEach {
                toggleTaskPinnedValueUseCase(it.id)
            }
        }
        clearSelectedTasks()
    }

    companion object {
        private const val CLEAR_SELECTED_TASKS_DELAY_MILLIS: Long = 150
    }
}
