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

package dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.task.DeleteTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.InsertTaskUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.DeleteTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.UpdateTaskListNameUseCase
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

class TaskListTasksViewModel(
    private val taskListId: String,
    private val getTaskListTasksUseCase: GetTaskListTasksUseCase,
    private val getTaskListUseCase: GetTaskListUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val setTaskDoingUseCase: SetTaskDoingUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    private val updateTaskListNameUseCase: UpdateTaskListNameUseCase,
    private val deleteTasksUseCase: DeleteTasksUseCase,
    private val deleteTaskListUseCase: DeleteTaskListUseCase,
) : ViewModel() {

    var state by mutableStateOf(
        TaskListTasksState(
            isLoadingTaskList = true,
            isLoadingTasks = true,
        ),
    )
        private set

    init {
        getTaskList()
        getTaskListTasks()
    }

    private fun getTaskList() = viewModelScope.launch {
        getTaskListUseCase(taskListId).collect { result ->
            result.doIfSuccess { taskList ->
                state = state.copy(
                    isLoadingTaskList = false,
                    taskList = taskList,
                    isDefaultTaskList = false,
                )
            }.doIfError {
                state = state.copy(
                    isLoadingTaskList = false,
                    taskList = null,
                    isDefaultTaskList = true,
                )
            }
        }
    }

    private fun getTaskListTasks() = viewModelScope.launch {
        getTaskListTasksUseCase(taskListId).collect { result ->
            result.doIfSuccess { tasks ->
                state = state.copy(
                    isLoadingTasks = false,
                    tasks = tasks.toPersistentList(),
                )
            }.doIfError {
                state = state.copy(
                    isLoadingTasks = false,
                    tasks = persistentListOf(),
                )
            }
        }
    }

    fun insertTask(title: String) = viewModelScope.launch {
        insertTaskUseCase.invoke(taskListId, title)
    }

    fun setTaskDoing(id: String) = viewModelScope.launch {
        setTaskDoingUseCase(id)
    }

    fun setTaskDone(id: String) = viewModelScope.launch {
        setTaskDoneUseCase(id)
    }

    fun updateTaskListName(name: String) = viewModelScope.launch {
        updateTaskListNameUseCase(taskListId, name)
    }

    fun deleteTask(id: String) = viewModelScope.launch {
        deleteTasksUseCase(id)
    }

    fun deleteTaskList() = viewModelScope.launch {
        deleteTaskListUseCase(taskListId)
    }
}
