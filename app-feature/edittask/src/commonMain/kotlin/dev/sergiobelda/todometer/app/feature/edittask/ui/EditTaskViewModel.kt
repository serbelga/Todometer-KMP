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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.UpdateTaskUseCase
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import dev.sergiobelda.todometer.common.viewmodel.ViewModel
import kotlinx.coroutines.launch

class EditTaskViewModel(
    private val taskId: String,
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    var editTaskUiState by mutableStateOf(EditTaskUiState(isLoading = true))
        private set

    init {
        getTask()
    }

    private fun getTask() = coroutineScope.launch {
        getTaskUseCase(taskId).collect { result ->
            result.doIfSuccess { task ->
                editTaskUiState = editTaskUiState.copy(
                    isLoading = false,
                    task = task,
                    errorUi = null
                )
            }.doIfError { error ->
                editTaskUiState = editTaskUiState.copy(
                    isLoading = false,
                    task = null,
                    errorUi = error.mapToErrorUi()
                )
            }
        }
    }

    fun updateTask(
        title: String,
        tag: Tag,
        description: String? = null,
        dueDate: Long? = null
    ) = coroutineScope.launch {
        editTaskUiState.task?.let {
            updateTaskUseCase(
                it.copy(
                    title = title,
                    tag = tag,
                    description = description,
                    dueDate = dueDate
                )
            )
        }
    }
}
