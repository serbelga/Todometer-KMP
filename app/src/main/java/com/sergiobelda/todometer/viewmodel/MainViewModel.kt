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
import androidx.lifecycle.viewModelScope
import com.sergiobelda.todometer.model.Project
import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.model.TaskProject
import com.sergiobelda.todometer.model.TaskState
import com.sergiobelda.todometer.repository.ProjectRepository
import com.sergiobelda.todometer.repository.TaskProjectRepository
import com.sergiobelda.todometer.repository.TaskRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository,
    private val taskProjectRepository: TaskProjectRepository
) : ViewModel() {

    var tasks: List<Task> by mutableStateOf(listOf())
        private set

    var taskProjectList: List<TaskProject> by mutableStateOf(listOf())
        private set

    var projectTasksList: List<Project> by mutableStateOf(listOf())
        private set

    val updateTaskState: (Int, TaskState) -> Unit = { id, taskState -> updateTaskState(id, taskState) }

    init {
        viewModelScope.launch {
            taskRepository.tasks.collect {
                tasks = it
            }
        }

        viewModelScope.launch {
            projectRepository.projectTaskList.collect {
                projectTasksList = it
            }
        }
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
    }

    private fun updateTaskState(id: Int, taskState: TaskState) = viewModelScope.launch {
        taskRepository.updateTaskState(id, taskState)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    fun deleteTask(id: Int) = viewModelScope.launch {
        taskRepository.deleteTask(id)
    }
}
