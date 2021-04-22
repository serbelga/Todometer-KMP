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

package com.sergiobelda.todometer.common.repository

import com.sergiobelda.todometer.common.database.dao.ITaskDao
import com.sergiobelda.todometer.common.database.mapper.TaskMapper.toDomain
import com.sergiobelda.todometer.common.database.mapper.TaskMapper.toEntity
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.model.TaskState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val taskDao: ITaskDao) : ITaskRepository {

    override fun getTask(id: Long): Flow<Task?> =
        taskDao.getTask(id).map { it?.toDomain() }

    override fun getTasks(): Flow<List<Task>> =
        taskDao.getTasks().map { list -> list.map { it.toDomain() } }

    override suspend fun insertTask(task: Task) =
        taskDao.insertTask(task.toEntity())

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task.toEntity())

    override suspend fun updateTaskState(id: Long, state: TaskState) =
        taskDao.updateTaskState(id, state)

    override suspend fun deleteTask(id: Long) =
        taskDao.deleteTask(id)
}
