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

package dev.sergiobelda.todometer.ui.addtasklist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.compose.ui.addtasklist.AddTaskListUiState
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.InsertTaskListUseCase
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import kotlinx.coroutines.launch

class AddTaskListViewModel(
    private val insertTaskListUseCase: InsertTaskListUseCase
) : ViewModel() {

    var addTaskListUiState by mutableStateOf(AddTaskListUiState())
        private set

    fun insertTaskList(name: String) = viewModelScope.launch {
        addTaskListUiState = addTaskListUiState.copy(isAddingTaskList = true)
        val result = insertTaskListUseCase.invoke(name)
        result.doIfSuccess {
            addTaskListUiState = addTaskListUiState.copy(
                isAddingTaskList = false,
                isAdded = true,
                errorUi = null
            )
        }.doIfError { error ->
            addTaskListUiState = addTaskListUiState.copy(
                isAddingTaskList = false,
                isAdded = false,
                errorUi = error.mapToErrorUi()
            )
        }
    }
}
