/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.app.feature.taskdetails.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.ToggleTaskPinnedValueUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.DeleteTaskChecklistItemUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.GetTaskChecklistItemsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.InsertTaskChecklistItemUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.SetTaskChecklistItemCheckedUseCase
import dev.sergiobelda.todometer.common.domain.usecase.taskchecklistitem.SetTaskChecklistItemUncheckedUseCase
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    private val taskId: String,
    private val getTaskUseCase: GetTaskUseCase,
    private val getTaskChecklistItemsUseCase: GetTaskChecklistItemsUseCase,
    private val insertTaskChecklistItemsUseCase: InsertTaskChecklistItemUseCase,
    private val deleteTaskChecklistItemUseCase: DeleteTaskChecklistItemUseCase,
    private val setTaskChecklistItemUncheckedUseCase: SetTaskChecklistItemUncheckedUseCase,
    private val setTaskChecklistItemCheckedUseCase: SetTaskChecklistItemCheckedUseCase,
    private val toggleTaskPinnedValueUseCase: ToggleTaskPinnedValueUseCase,
) : ViewModel() {

    var state by mutableStateOf(
        TaskDetailsState(
            isLoadingTask = true,
            isLoadingTaskChecklistItems = true,
        ),
    )
        private set

    init {
        getTask()
        getTaskChecklistItems()
    }

    private fun getTask() = viewModelScope.launch {
        getTaskUseCase(taskId).collect { result ->
            result.doIfSuccess { task ->
                state = state.copy(
                    isLoadingTask = false,
                    task = task,
                    errorUi = null,
                )
            }.doIfError { error ->
                state = state.copy(
                    isLoadingTask = false,
                    task = null,
                    errorUi = error.mapToErrorUi(),
                )
            }
        }
    }

    private fun getTaskChecklistItems() = viewModelScope.launch {
        getTaskChecklistItemsUseCase(taskId).collect { result ->
            result.doIfSuccess { taskChecklistItems ->
                state = state.copy(
                    isLoadingTaskChecklistItems = false,
                    taskChecklistItems = taskChecklistItems.toPersistentList(),
                )
            }.doIfError {
                state = state.copy(
                    isLoadingTaskChecklistItems = false,
                    taskChecklistItems = persistentListOf(),
                )
            }
        }
    }

    fun insertTaskChecklistItem(text: String) = viewModelScope.launch {
        insertTaskChecklistItemsUseCase(taskId, text)
    }

    fun setTaskChecklistItemUnchecked(id: String) = viewModelScope.launch {
        setTaskChecklistItemUncheckedUseCase(id)
    }

    fun setTaskChecklistItemChecked(id: String) = viewModelScope.launch {
        setTaskChecklistItemCheckedUseCase(id)
    }

    fun deleteTaskChecklistItem(id: String) = viewModelScope.launch {
        deleteTaskChecklistItemUseCase(id)
    }

    fun toggleTaskPinnedValueUseCase() = viewModelScope.launch {
        toggleTaskPinnedValueUseCase(taskId)
    }
}
