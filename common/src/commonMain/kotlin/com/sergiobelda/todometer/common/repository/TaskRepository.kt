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

package com.sergiobelda.todometer.common.repository

import com.sergiobelda.todometer.common.data.Result
import com.sergiobelda.todometer.common.data.doIfError
import com.sergiobelda.todometer.common.data.doIfSuccess
import com.sergiobelda.todometer.common.localdatasource.ITaskLocalDataSource
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.model.TaskState
import com.sergiobelda.todometer.common.remotedatasource.ITaskRemoteDataSource
import com.sergiobelda.todometer.common.util.randomUUIDString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(
    private val taskLocalDataSource: ITaskLocalDataSource,
    private val taskRemoteDataSource: ITaskRemoteDataSource
) : ITaskRepository {

    override fun getTask(id: String): Flow<Result<Task?>> =
        taskLocalDataSource.getTask(id)

    override suspend fun getTasks(projectId: String?): Flow<Result<List<Task>>> =
        taskLocalDataSource.getTasks(projectId).map { result ->
            result.doIfSuccess { tasks ->
                tasks.filter { !it.sync }.forEach { task ->
                    synchronizeTaskRemotely(task)
                }
            }
        }

    private suspend fun synchronizeTaskRemotely(task: Task) {
        val result = taskRemoteDataSource.insertTask(
            id = task.id,
            title = task.title,
            description = task.description,
            projectId = task.projectId,
            state = task.state,
            tag = task.tag
        )
        result.doIfSuccess {
            taskLocalDataSource.updateTask(
                task.copy(sync = true)
            )
        }
    }

    override suspend fun refreshTasks(projectId: String) {
        val result = taskRemoteDataSource.getTasks(projectId)
        result.doIfSuccess { list ->
            taskLocalDataSource.insertTasks(list)
        }
    }

    override suspend fun insertTask(
        title: String,
        description: String,
        projectId: String,
        tag: Tag
    ): Result<String> {
        var sync = false
        // TODO Set null to indicate DAO need to generate UUID
        var taskId = randomUUIDString()
        taskRemoteDataSource.insertTask(
            title = title,
            description = description,
            projectId = projectId,
            tag = tag
        ).doIfSuccess {
            sync = true
            taskId = it
        }
        return taskLocalDataSource.insertTask(
            Task(
                id = taskId,
                title = title,
                description = description,
                state = TaskState.DOING,
                projectId = projectId,
                tag = tag,
                sync = sync
            )
        )
    }

    override suspend fun updateTask(task: Task) =
        taskLocalDataSource.updateTask(task)

    override suspend fun updateTaskState(id: String, state: TaskState) {
        taskRemoteDataSource.updateTaskState(id, state).doIfError {
            taskLocalDataSource.updateTaskSync(id, false)
        }
        taskLocalDataSource.updateTaskState(id, state)
    }

    override suspend fun deleteTask(id: String) =
        taskRemoteDataSource.deleteTask(id).doIfSuccess {
            taskLocalDataSource.deleteTask(id)
        }
}
