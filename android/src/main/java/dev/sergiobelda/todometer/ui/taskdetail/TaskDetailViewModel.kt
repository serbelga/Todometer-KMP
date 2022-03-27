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

package dev.sergiobelda.todometer.ui.taskdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    taskId: String,
    getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    var taskDetailUiState by mutableStateOf(TaskDetailUiState(isLoading = true))
        private set

    init {
        viewModelScope.launch {
            getTaskUseCase(taskId).collect { result ->
                result.doIfSuccess { task ->
                    taskDetailUiState = taskDetailUiState.copy(
                        isLoading = false,
                        task = task,
                        errorMessage = null
                    )
                }.doIfError { error ->
                    taskDetailUiState = taskDetailUiState.copy(
                        isLoading = false,
                        task = null,
                        errorMessage = error.message
                    )
                }
            }
        }
    }
}

data class TaskDetailUiState(
    val isLoading: Boolean = false,
    val task: Task? = null,
    val errorMessage: String? = null
)
