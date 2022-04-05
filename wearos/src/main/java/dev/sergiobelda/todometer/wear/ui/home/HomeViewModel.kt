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

package dev.sergiobelda.todometer.wear.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.InsertTaskListUseCase
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTaskListsUseCase: GetTaskListsUseCase,
    private val insertTaskListUseCase: InsertTaskListUseCase
) : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState(isLoading = true))
        private set

    init {
        getTaskLists()
    }

    private fun getTaskLists() = viewModelScope.launch {
        getTaskListsUseCase().collect { result ->
            result.doIfSuccess { taskLists ->
                homeUiState = homeUiState.copy(
                    isLoading = false,
                    taskLists = taskLists,
                    errorUi = null
                )
            }.doIfError { error ->
                homeUiState = homeUiState.copy(
                    isLoading = false,
                    taskLists = emptyList(),
                    errorUi = error.mapToErrorUi()
                )
            }
        }
    }

    fun insertTaskList(name: String) = viewModelScope.launch {
        insertTaskListUseCase.invoke(name)
    }
}
