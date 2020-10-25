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

package com.sergiobelda.todometer.repository

import com.sergiobelda.todometer.db.dao.TaskDao
import com.sergiobelda.todometer.mapper.TaskMapper.toDomain
import com.sergiobelda.todometer.mapper.TaskMapper.toEntity
import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.model.TaskState
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: TaskDao) {
    val tasks = taskDao.getTasks().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun deleteTask(id: Int) = taskDao.deleteTask(id)

    suspend fun insert(task: Task) = taskDao.insertTask(task.toEntity())

    suspend fun updateTaskState(id: Int, taskState: TaskState) =
        taskDao.updateTaskState(id, taskState.name)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task.toEntity())
}
