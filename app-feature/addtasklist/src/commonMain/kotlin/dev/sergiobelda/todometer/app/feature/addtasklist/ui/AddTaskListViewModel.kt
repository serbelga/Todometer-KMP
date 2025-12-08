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

package dev.sergiobelda.todometer.app.feature.addtasklist.ui

import androidx.lifecycle.viewModelScope
import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.fonament.presentation.ui.FonamentViewModel
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.InsertTaskListUseCase
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import kotlinx.coroutines.launch

class AddTaskListViewModel(
    private val insertTaskListUseCase: InsertTaskListUseCase,
) : FonamentViewModel<AddTaskListUIState>(
        initialUIState = AddTaskListUIState(),
    ) {
    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            is AddTaskListEvent.InsertTaskList -> {
                insertTaskList(event.name)
            }
        }
    }

    private fun insertTaskList(name: String) =
        viewModelScope.launch {
            updateUIState {
                it.copy(isAddingTaskList = true)
            }
            if (name.isNotBlank()) {
                val result = insertTaskListUseCase.invoke(name)
                result
                    .doIfSuccess {
                        updateUIState {
                            it.copy(
                                isAddingTaskList = false,
                                errorUi = null,
                            )
                        }
                    }.doIfError { error ->
                        updateUIState {
                            it.copy(
                                isAddingTaskList = false,
                                errorUi = error.mapToErrorUi(),
                            )
                        }
                    }
            }
        }
}
