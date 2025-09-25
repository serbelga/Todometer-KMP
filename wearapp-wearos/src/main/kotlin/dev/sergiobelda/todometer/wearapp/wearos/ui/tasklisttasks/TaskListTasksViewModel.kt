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

import androidx.lifecycle.viewModelScope
import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import dev.sergiobelda.fonament.presentation.ui.FonamentViewModel
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
) : FonamentViewModel<TaskListTasksUIState>(
    initialUIState = TaskListTasksUIState(
        taskListUIState = TaskListUIState.Loading,
        tasksUIState = TasksUIState.Loading,
    ),
) {

    init {
        getTaskList()
        getTaskListTasks()
    }

    private fun getTaskList() {
        if (taskListId.isEmpty()) {
            updateUIState {
                it.copy(
                    taskListUIState = TaskListUIState.DefaultTaskList,
                )
            }
        } else {
            viewModelScope.launch {
                getTaskListUseCase(taskListId).collect { result ->
                    result.doIfSuccess { taskList ->
                        updateUIState {
                            it.copy(
                                taskListUIState = TaskListUIState.Success(taskList),
                            )
                        }
                    }.doIfError {
                        updateUIState {
                            it.copy(
                                taskListUIState = TaskListUIState.Error(errorUi = null),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getTaskListTasks() = viewModelScope.launch {
        getTaskListTasksUseCase(taskListId).collect { result ->
            result.doIfSuccess { tasks ->
                updateUIState {
                    it.copy(
                        tasksUIState = TasksUIState.Success(tasks.toPersistentList()),
                    )
                }
            }.doIfError {
                updateUIState {
                    it.copy(
                        tasksUIState = TasksUIState.Error(errorUi = null),
                    )
                }
            }
        }
    }

    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            is TaskListTasksEvent.InsertTask -> insertTask(event.title)
            is TaskListTasksEvent.SetTaskDoing -> setTaskDoing(event.taskId)
            is TaskListTasksEvent.SetTaskDone -> setTaskDone(event.taskId)
            is TaskListTasksEvent.UpdateTaskListName -> updateTaskListName(event.name)
            is TaskListTasksEvent.DeleteTask -> deleteTask(event.taskId)
            is TaskListTasksEvent.DeleteTaskList -> deleteTaskList()
        }
    }

    private fun insertTask(title: String) = viewModelScope.launch {
        insertTaskUseCase.invoke(taskListId, title)
    }

    private fun setTaskDoing(id: String) = viewModelScope.launch {
        setTaskDoingUseCase(id)
    }

    private fun setTaskDone(id: String) = viewModelScope.launch {
        setTaskDoneUseCase(id)
    }

    private fun updateTaskListName(name: String) = viewModelScope.launch {
        updateTaskListNameUseCase(taskListId, name)
    }

    private fun deleteTask(id: String) = viewModelScope.launch {
        deleteTasksUseCase(id)
    }

    private fun deleteTaskList() = viewModelScope.launch {
        deleteTaskListUseCase(taskListId)
    }
}
