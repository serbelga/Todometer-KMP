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

package dev.sergiobelda.todometer.backend.service

import dev.sergiobelda.todometer.backend.model.NewTask
import dev.sergiobelda.todometer.backend.model.Task
import dev.sergiobelda.todometer.backend.model.TaskState

interface ITaskService {

    suspend fun getTasks(taskListId: String?): List<Task>

    suspend fun getTask(id: String): Task

    suspend fun insertTask(newTask: NewTask): String

    suspend fun updateTask(id: String, taskState: TaskState)

    suspend fun deleteTask(id: String)
}
