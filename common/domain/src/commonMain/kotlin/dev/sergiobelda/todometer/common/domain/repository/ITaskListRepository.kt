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
import dev.sergiobelda.todometer.common.domain.model.TaskList
import kotlinx.coroutines.flow.Flow

interface ITaskListRepository {
    /**
     * Get a task list given its [id].
     */
    fun getTaskList(id: String): Flow<Result<TaskList>>

    /**
     * Get the list of all [TaskList].
     */
    fun getTaskLists(): Flow<Result<List<TaskList>>>

    /**
     * Inserts a [TaskList] given a [name].
     */
    suspend fun insertTaskList(name: String): Result<String>

    /**
     * Updates a [TaskList] given a [taskList] object.
     */
    suspend fun updateTaskList(taskList: TaskList)

    /**
     * Updates the name of a Task List given a new [name].
     */
    suspend fun updateTaskListName(
        id: String,
        name: String,
    )

    /**
     * Deletes a [TaskList] given its [id].
     */
    suspend fun deleteTaskList(id: String)
}
