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

package dev.sergiobelda.todometer.app.feature.edittask.ui

import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.UpdateTaskUseCase
import dev.sergiobelda.todometer.common.ui.base.BaseEvent
import dev.sergiobelda.todometer.common.ui.base.BaseViewModel
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import kotlinx.coroutines.launch

class EditTaskViewModel(
    private val taskId: String,
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : BaseViewModel<EditTaskUIState>(
    initialUIState = EditTaskUIState(
        isLoading = true,
    ),
) {

    init {
        getTask()
    }

    private fun getTask() = viewModelScope.launch {
        getTaskUseCase(taskId).collect { result ->
            result.doIfSuccess { task ->
                updateUIState {
                    it.copy(
                        isLoading = false,
                        task = task,
                        errorUi = null,
                    )
                }
            }.doIfError { error ->
                updateUIState {
                    it.copy(
                        isLoading = false,
                        task = null,
                        errorUi = error.mapToErrorUi(),
                    )
                }
            }
        }
    }

    override fun handleEvent(event: BaseEvent) {
        when (event) {
            is EditTaskEvent.UpdateTask -> updateTask(event)
        }
    }

    private fun updateTask(
        event: EditTaskEvent.UpdateTask,
    ) = viewModelScope.launch {
        uiState.task?.let {
            updateTaskUseCase(
                it.copy(
                    title = event.title,
                    tag = event.tag,
                    description = event.description,
                    dueDate = event.dueDate,
                ),
            )
        }
    }
}
