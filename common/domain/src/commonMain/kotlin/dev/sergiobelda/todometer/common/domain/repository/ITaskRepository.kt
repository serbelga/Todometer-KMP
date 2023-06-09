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

package dev.sergiobelda.todometer.common.domain.repository

import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {

    /**
     * Get a task given its [id].
     */
    fun getTask(id: String): Flow<Result<Task>>

    /**
     * Get a list of [TaskItem] given a [taskListId].
     */
    fun getTasks(taskListId: String): Flow<Result<List<TaskItem>>>

    suspend fun refreshTasks(taskListId: String)

    /**
     * Inserts a [Task] given a [title], [description], [taskListId] and [tag].
     */
    suspend fun insertTask(
        title: String,
        tag: Tag,
        description: String?,
        dueDate: Long?,
        taskListId: String
    ): Result<String>

    /**
     * Updates a [Task] given a [task] object.
     */
    suspend fun updateTask(task: Task)

    /**
     * Updates a [Task] given its [id] to [state].
     */
    suspend fun updateTaskState(id: String, state: TaskState)

    /**
     * Deletes a [Task] given its [id].
     */
    suspend fun deleteTasks(vararg ids: String)
}
