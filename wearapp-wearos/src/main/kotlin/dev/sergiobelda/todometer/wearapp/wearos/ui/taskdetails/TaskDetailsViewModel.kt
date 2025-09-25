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

package dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetails

import androidx.lifecycle.viewModelScope
import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.fonament.presentation.ui.FonamentViewModel
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.task.DeleteTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.UpdateTaskUseCase
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    private val taskId: String,
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
) : FonamentViewModel<TaskDetailsUIState>(
    initialUIState = TaskDetailsUIState.Loading,
) {
    init { getTask() }

    private fun getTask() = viewModelScope.launch {
        getTaskUseCase(taskId).collect { result ->
            result.doIfSuccess { task ->
                updateUIState {
                    TaskDetailsUIState.Success(task)
                }
            }.doIfError { error ->
                updateUIState {
                    TaskDetailsUIState.Error(
                        errorUi = error.mapToErrorUi(),
                    )
                }
            }
        }
    }

    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            TaskDetailsEvent.DeleteTask -> { deleteTask() }
            is TaskDetailsEvent.UpdateTask -> { updateTask(event.title) }
        }
    }

    private fun updateTask(title: String) = viewModelScope.launch {
        (uiState as? TaskDetailsUIState.Success)?.let {
            updateTaskUseCase(it.task.copy(title = title))
        }
    }

    private fun deleteTask() = viewModelScope.launch {
        deleteTasksUseCase(taskId)
    }
}
