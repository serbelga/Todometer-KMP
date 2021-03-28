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

package com.sergiobelda.todometer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.model.TaskState
import com.sergiobelda.todometer.common.usecase.DeleteTaskUseCase
import com.sergiobelda.todometer.common.usecase.GetProjectsUseCase
import com.sergiobelda.todometer.common.usecase.GetTaskUseCase
import com.sergiobelda.todometer.common.usecase.GetTasksUseCase
import com.sergiobelda.todometer.common.usecase.InsertTaskUseCase
import com.sergiobelda.todometer.common.usecase.UpdateTaskStateUseCase
import com.sergiobelda.todometer.common.usecase.UpdateTaskUseCase
import com.sergiobelda.todometer.model.Project
import com.sergiobelda.todometer.usecase.GetProjectUseCase
import com.sergiobelda.todometer.usecase.InsertProjectUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val getProjectUseCase: GetProjectUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val insertProjectUseCase: InsertProjectUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val updateTaskStateUseCase: UpdateTaskStateUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getProjectsUseCase: GetProjectsUseCase,
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val tasks: LiveData<List<com.sergiobelda.todometer.common.model.Task>> = getTasksUseCase().asLiveData()

    val projects: LiveData<List<com.sergiobelda.todometer.common.model.Project>> = getProjectsUseCase().asLiveData()

    fun getTask(id: Long) = getTaskUseCase(id).asLiveData()

    // TODO: 28/03/2021 Update
    val updateTaskState: (Long, TaskState) -> Unit = { id, taskState -> updateTaskState(id, taskState) }

    fun insertTask(task: Task) = viewModelScope.launch {
        insertTaskUseCase(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        updateTaskUseCase(task)
    }

    fun deleteTask(id: Long) = viewModelScope.launch {
        deleteTaskUseCase(id)
    }

    // TODO Migrate from here
    fun insertProject(project: Project) = viewModelScope.launch {
        insertProjectUseCase(project)
    }

    private fun updateTaskState(id: Long, taskState: TaskState) = viewModelScope.launch {
        updateTaskStateUseCase(id, taskState)
    }

    fun getProject(id: Int) = getProjectUseCase(id).asLiveData()
}
