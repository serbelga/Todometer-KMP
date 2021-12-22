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

package dev.sergiobelda.todometer.common.api.client

import dev.sergiobelda.todometer.common.api.model.TaskListApiModel
import dev.sergiobelda.todometer.common.api.request.NewTaskListRequestBody

interface ITaskListApiClient {

    suspend fun getTaskLists(): List<TaskListApiModel>

    suspend fun getTaskList(id: String): TaskListApiModel

    suspend fun insertTaskList(newTaskListRequestBody: NewTaskListRequestBody): String

    suspend fun updateTaskList(id: String, name: String, description: String)

    suspend fun deleteTaskList(id: String): String
}
