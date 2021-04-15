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
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.usecase.DeleteTaskUseCase
import com.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.GetProjectsUseCase
import com.sergiobelda.todometer.common.usecase.GetTaskUseCase
import com.sergiobelda.todometer.common.usecase.GetTasksUseCase
import com.sergiobelda.todometer.common.usecase.InsertProjectUseCase
import com.sergiobelda.todometer.common.usecase.InsertTaskUseCase
import com.sergiobelda.todometer.common.usecase.SetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import com.sergiobelda.todometer.common.usecase.UpdateTaskUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val insertTaskUseCase: InsertTaskUseCase,
    private val insertProjectUseCase: InsertProjectUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val setTaskDoingUseCase: SetTaskDoingUseCase,
    private val setTaskDoneUseCase: SetTaskDoneUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val setProjectSelectedUseCase: SetProjectSelectedUseCase,
    getProjectSelectedUseCase: GetProjectSelectedUseCase,
    getProjectsUseCase: GetProjectsUseCase,
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val tasks: LiveData<List<Task>> = getTasksUseCase().asLiveData()

    val projects: LiveData<List<Project>> = getProjectsUseCase().asLiveData()

    val projectSelected: LiveData<Project?> = getProjectSelectedUseCase().asLiveData()

    fun getTask(id: Long) = getTaskUseCase(id).asLiveData()

    fun insertTask(
        title: String,
        description: String?,
        tagId: Long?
    ) = viewModelScope.launch {
        insertTaskUseCase(
            title,
            description,
            tagId
        )
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        updateTaskUseCase(task)
    }

    fun deleteTask(id: Long) = viewModelScope.launch {
        deleteTaskUseCase(id)
    }

    fun insertProject(name: String, description: String) = viewModelScope.launch {
        insertProjectUseCase(name, description)
    }

    fun setTaskDoing(id: Long) = viewModelScope.launch {
        setTaskDoingUseCase(id)
    }

    fun setTaskDone(id: Long) = viewModelScope.launch {
        setTaskDoneUseCase(id)
    }

    fun setProjectSelected(id: Long) = viewModelScope.launch {
        setProjectSelectedUseCase(id)
    }
}
