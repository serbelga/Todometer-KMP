/*
 * Copyright 2020 Sergio Belda
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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sergiobelda.todometer.model.Project
import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.model.TaskState
import com.sergiobelda.todometer.usecase.DeleteTaskUseCase
import com.sergiobelda.todometer.usecase.GetProjectListUseCase
import com.sergiobelda.todometer.usecase.GetTaskUseCase
import com.sergiobelda.todometer.usecase.InsertProjectUseCase
import com.sergiobelda.todometer.usecase.InsertTaskUseCase
import com.sergiobelda.todometer.usecase.UpdateTaskStateUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getTaskUseCase: GetTaskUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val insertProjectUseCase: InsertProjectUseCase,
    private val getProjectListUseCase: GetProjectListUseCase,
    private val updateTaskStateUseCase: UpdateTaskStateUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    var projectList: List<Project> by mutableStateOf(listOf())
        private set

    val updateTaskState: (Int, TaskState) -> Unit = { id, taskState -> updateTaskState(id, taskState) }

    init {
        viewModelScope.launch {
            getProjectListUseCase.getProjectTasksList().collect {
                projectList = it
            }
        }
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        insertTaskUseCase.insertTask(task)
    }

    fun insertProject(project: Project) = viewModelScope.launch {
        insertProjectUseCase.insertProject(project)
    }

    private fun updateTaskState(id: Int, taskState: TaskState) = viewModelScope.launch {
        updateTaskStateUseCase.updateTaskState(id, taskState)
    }

    fun getTask(id: Int) = getTaskUseCase.getTask(id).asLiveData()

    fun deleteTask(id: Int) = viewModelScope.launch {
        deleteTaskUseCase.deleteTask(id)
    }
}
