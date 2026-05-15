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

import androidx.lifecycle.viewModelScope
import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.fonament.presentation.ui.FonamentViewModel
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
) : FonamentViewModel<HomeUIState>(
        initialUIState = HomeUIState(isLoadingTasks = true),
    ) {
    init {
        getTaskListSelected()
        getTaskListSelectedTasks()
        getTaskLists()
    }

    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            is HomeEvent.SetTaskDoing -> setTaskDoing(event.id)
            is HomeEvent.SetTaskDone -> setTaskDone(event.id)
            is HomeEvent.DeleteTask -> deleteTask(event.id)
            HomeEvent.DeleteSelectedTasks -> deleteSelectedTasks()
            HomeEvent.DeleteTaskList -> deleteTaskList()
            is HomeEvent.SetTaskListSelected -> setTaskListSelected(event.id)
            is HomeEvent.ToggleSelectTask -> toggleSelectTask(event.id)
            HomeEvent.ClearSelectedTasks -> clearSelectedTasks()
            HomeEvent.ToggleSelectedTasksPinnedValue -> toggleSelectedTasksPinnedValue()
        }
    }

    private fun getTaskListSelected() =
        viewModelScope.launch {
            getTaskListSelectedUseCase().collect { result ->
                result
                    .doIfSuccess { taskList ->
                        updateUIState {
                            it.copy(
                                taskListSelected = taskList,
                            )
                        }
                    }.doIfError {
                        updateUIState {
                            it.copy(
                                taskListSelected = null,
                            )
                        }
                    }
            }
        }

    private fun getTaskListSelectedTasks() =
        viewModelScope.launch {
            getTaskListSelectedTasksUseCase().collect { result ->
                result
                    .doIfSuccess { tasks ->
                        updateUIState {
                            it.copy(
                                isLoadingTasks = false,
                                tasks = tasks.toPersistentList(),
                            )
                        }
                    }.doIfError {
                        updateUIState {
                            it.copy(
                                isLoadingTasks = false,
                                tasks = persistentListOf(),
                            )
                        }
                    }
            }
        }

    private fun getTaskLists() =
        viewModelScope.launch {
            getTaskListsUseCase().collect { result ->
                result
                    .doIfSuccess { taskLists ->
                        updateUIState {
                            it.copy(
                                taskLists = taskLists.toPersistentList(),
                            )
                        }
                    }.doIfError {
                        updateUIState {
                            it.copy(
                                taskLists = persistentListOf(),
                            )
                        }
                    }
            }
        }

    private fun deleteSelectedTasks() =
        viewModelScope.launch {
            deleteTasksUseCase(uiState.selectedTasksIds)
            clearSelectedTasks()
        }

    private fun deleteTask(id: String) =
        viewModelScope.launch {
            deleteTasksUseCase(id)
        }

    private fun deleteTaskList() =
        viewModelScope.launch {
            deleteTaskListSelectedUseCase()
        }

    private fun setTaskDoing(id: String) =
        viewModelScope.launch {
            setTaskDoingUseCase(id)
        }

    private fun setTaskDone(id: String) =
        viewModelScope.launch {
            setTaskDoneUseCase(id)
        }

    private fun setTaskListSelected(id: String) =
        viewModelScope.launch {
            setTaskListSelectedUseCase(id)
        }

    private fun toggleSelectTask(id: String) {
        val selectedTasksIds = uiState.selectedTasksIds.toMutableList()
        if (!selectedTasksIds.contains(id)) {
            selectedTasksIds.add(id)
        } else {
            selectedTasksIds.removeAll { it == id }
        }
        updateUIState {
            it.copy(
                selectedTasksIds = selectedTasksIds.toPersistentList(),
            )
        }
    }

    private fun clearSelectedTasks() =
        viewModelScope.launch {
            delay(CLEAR_SELECTED_TASKS_DELAY_MILLIS)
            updateUIState {
                it.copy(selectedTasksIds = persistentListOf())
            }
        }

    private fun toggleSelectedTasksPinnedValue() =
        viewModelScope.launch {
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
