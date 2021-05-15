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

package com.sergiobelda.todometer.common.database.dao

import com.sergiobelda.todometer.TaskEntity
import com.sergiobelda.todometer.TaskTagView
import com.sergiobelda.todometer.common.model.TaskState
import kotlinx.coroutines.flow.Flow

interface ITaskDao {

    fun getTask(id: Long): Flow<TaskTagView?>

    fun getTasks(): Flow<List<TaskTagView>>

    suspend fun insertTask(task: TaskEntity)

    suspend fun updateTask(task: TaskEntity)

    suspend fun updateTaskState(id: Long, state: TaskState)

    suspend fun deleteTask(id: Long)
}
