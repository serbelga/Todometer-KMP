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

import dev.sergiobelda.todometer.backend.database.dao.ITaskDao
import dev.sergiobelda.todometer.backend.model.NewTask
import dev.sergiobelda.todometer.backend.model.Task
import dev.sergiobelda.todometer.backend.model.TaskState
import dev.sergiobelda.todometer.backend.model.toNewTaskEntity
import dev.sergiobelda.todometer.backend.model.toTask
import dev.sergiobelda.todometer.backend.model.toTaskList
import java.util.UUID

class TaskService(private val taskDao: ITaskDao) : ITaskService {

    override suspend fun getTasks(taskListId: String?): List<Task> =
        taskDao.getTasks(taskListId?.let { UUID.fromString(it) }).toTaskList()

    override suspend fun getTask(id: String): Task =
        taskDao.getTask(UUID.fromString(id)).toTask()

    override suspend fun insertTask(newTask: NewTask): String =
        taskDao.insertTask(newTask.toNewTaskEntity()).toString()

    override suspend fun updateTask(id: String, taskState: TaskState) {
        taskDao.updateTaskState(UUID.fromString(id), taskState.name)
    }

    override suspend fun deleteTask(id: String) = taskDao.deleteTask(UUID.fromString(id))
}
