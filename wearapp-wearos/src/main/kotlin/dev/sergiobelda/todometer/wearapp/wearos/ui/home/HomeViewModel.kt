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

package dev.sergiobelda.todometer.wearapp.wearos.ui.home

import androidx.lifecycle.viewModelScope
import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.fonament.presentation.ui.FonamentViewModel
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListsUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.InsertTaskListUseCase
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTaskListsUseCase: GetTaskListsUseCase,
    private val insertTaskListUseCase: InsertTaskListUseCase,
) : FonamentViewModel<HomeUIState>(
    initialUIState = HomeUIState.Loading,
) {

    init { getTaskLists() }

    private fun getTaskLists() {
        viewModelScope.launch {
            getTaskListsUseCase().collect { result ->
                result.doIfSuccess { taskLists ->
                    updateUIState {
                        HomeUIState.Success(taskLists.toPersistentList())
                    }
                }.doIfError { error ->
                    updateUIState {
                        HomeUIState.Error(
                            errorUi = error.mapToErrorUi(),
                        )
                    }
                }
            }
        }
    }

    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            is HomeEvent.InsertTaskList -> {
                insertTaskList(event.name)
            }
        }
    }

    private fun insertTaskList(name: String) = viewModelScope.launch {
        insertTaskListUseCase.invoke(name)
    }
}
