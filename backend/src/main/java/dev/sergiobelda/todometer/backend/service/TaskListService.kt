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

import dev.sergiobelda.todometer.backend.database.dao.ITaskListDao
import dev.sergiobelda.todometer.backend.model.NewTaskList
import dev.sergiobelda.todometer.backend.model.TaskList
import dev.sergiobelda.todometer.backend.model.toNewTaskListEntity
import dev.sergiobelda.todometer.backend.model.toTaskList
import dev.sergiobelda.todometer.backend.model.toTaskListEntity
import dev.sergiobelda.todometer.backend.model.toTaskListList
import java.util.UUID

class TaskListService(private val taskListDao: ITaskListDao) : ITaskListService {

    override suspend fun getTaskList(id: String): TaskList =
        taskListDao.getTaskList(UUID.fromString(id)).toTaskList()

    override suspend fun getTaskLists(): List<TaskList> = taskListDao.getTaskLists().toTaskListList()

    override suspend fun insertTaskList(newTaskList: NewTaskList): String =
        taskListDao.insertTaskList(newTaskList.toNewTaskListEntity()).toString()

    override suspend fun updateTaskList(taskList: TaskList) =
        taskListDao.updateTaskList(taskList.toTaskListEntity())

    override suspend fun deleteTaskList(id: String) = taskListDao.deleteTaskList(UUID.fromString(id))
}
