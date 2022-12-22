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

package dev.sergiobelda.todometer.backend.database.dao

import dev.sergiobelda.todometer.backend.database.entity.NewTaskListEntity
import dev.sergiobelda.todometer.backend.database.entity.TaskListEntity
import java.util.UUID

interface ITaskListDao {

    suspend fun getTaskList(id: UUID): TaskListEntity

    suspend fun getTaskLists(): List<TaskListEntity>

    suspend fun insertTaskList(taskList: NewTaskListEntity): UUID

    suspend fun updateTaskList(taskList: TaskListEntity)

    suspend fun deleteTaskList(id: UUID)
}
