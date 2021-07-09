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

package com.sergiobelda.backend.service

import com.sergiobelda.backend.model.NewTask
import com.sergiobelda.backend.model.Task

interface ITaskService {

    suspend fun getTasks(projectId: String?): List<Task>

    suspend fun getTask(id: String): Task

    suspend fun insertTask(newTask: NewTask): String

    suspend fun deleteTask(id: String)
}
