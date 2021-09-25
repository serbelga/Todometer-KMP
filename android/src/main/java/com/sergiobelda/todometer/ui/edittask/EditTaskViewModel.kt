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

package com.sergiobelda.todometer.ui.edittask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiobelda.todometer.common.data.Result
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.usecase.GetTaskUseCase
import com.sergiobelda.todometer.common.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditTaskViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    fun getTask(id: String): StateFlow<Result<Task>> =
        getTaskUseCase(id).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), Result.Loading)

    fun updateTask(task: Task) = viewModelScope.launch {
        updateTaskUseCase(task)
    }
}
