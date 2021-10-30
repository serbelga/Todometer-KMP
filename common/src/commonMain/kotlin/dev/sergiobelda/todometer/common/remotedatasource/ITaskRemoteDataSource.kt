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

package dev.sergiobelda.todometer.common.remotedatasource

import dev.sergiobelda.todometer.common.data.Result
import dev.sergiobelda.todometer.common.model.Tag
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.model.TaskState

interface ITaskRemoteDataSource {

    suspend fun getTasks(projectId: String? = null): Result<List<Task>>

    suspend fun insertTask(
        id: String? = null,
        title: String,
        description: String?,
        state: TaskState = TaskState.DOING,
        projectId: String,
        tag: Tag
    ): Result<String>

    suspend fun updateTask()

    suspend fun updateTaskState(id: String, state: TaskState): Result<String>

    suspend fun deleteTask(id: String): Result<String>
}
