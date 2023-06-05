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

package dev.sergiobelda.todometer.common.database.dao

import dev.sergiobelda.todometer.common.database.SelectTasksByTaskListId
import dev.sergiobelda.todometer.common.database.TaskEntity
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlinx.coroutines.flow.Flow

interface ITaskDao {

    fun getTask(id: String): Flow<TaskEntity?>

    fun getTasks(taskListId: String): Flow<List<SelectTasksByTaskListId>>

    suspend fun insertTask(task: TaskEntity): String

    suspend fun insertTasks(tasks: List<TaskEntity>)

    suspend fun updateTask(task: TaskEntity)

    suspend fun updateTaskSync(id: String, sync: Boolean)

    suspend fun updateTaskState(id: String, state: TaskState)

    suspend fun deleteTasks(vararg ids: String)
}
