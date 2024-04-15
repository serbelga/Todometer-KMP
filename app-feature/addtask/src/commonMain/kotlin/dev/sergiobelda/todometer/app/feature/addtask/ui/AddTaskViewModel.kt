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

package dev.sergiobelda.todometer.app.feature.addtask.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.usecase.task.InsertTaskInTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val insertTaskInTaskListSelectedUseCase: InsertTaskInTaskListSelectedUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AddTaskUiState())
        private set

    fun insertTask(
        title: String,
        tag: Tag,
        description: String? = null,
        dueDate: Long? = null,
        taskChecklistItems: List<String> = emptyList()
    ) = viewModelScope.launch {
        uiState = uiState.copy(isAddingTask = true)
        val result = insertTaskInTaskListSelectedUseCase.invoke(
            title,
            tag,
            description,
            dueDate,
            taskChecklistItems
        )
        result.doIfSuccess {
            uiState = uiState.copy(
                isAddingTask = false,
                errorUi = null
            )
        }.doIfError { error ->
            uiState = uiState.copy(
                isAddingTask = false,
                errorUi = error.mapToErrorUi()
            )
        }
    }
}
