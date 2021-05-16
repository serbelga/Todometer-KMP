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

package com.sergiobelda.todometer.common.database.dao

import com.sergiobelda.todometer.TaskEntity
import com.sergiobelda.todometer.TaskTagView
import com.sergiobelda.todometer.TodometerDatabase
import com.sergiobelda.todometer.common.model.TaskState
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TaskDao : ITaskDao, KoinComponent {

    private val database: TodometerDatabase by inject()

    override fun getTask(id: Long): Flow<TaskTagView?> =
        database.todometerQueries.selectTask(id).asFlow().mapToOneOrNull()

    override fun getTasks(): Flow<List<TaskTagView>> =
        database.todometerQueries.selectAllTasks().asFlow().mapToList()

    override suspend fun insertTask(task: TaskEntity) =
        database.todometerQueries.insertTask(
            title = task.title,
            description = task.description,
            state = task.state,
            tag_id = task.tag_id,
            project_id = task.project_id
        )

    override suspend fun updateTask(task: TaskEntity) =
        database.todometerQueries.updateTask(
            id = task.id,
            title = task.title,
            description = task.description,
            tag_id = task.tag_id
        )

    override suspend fun updateTaskState(id: Long, state: TaskState) {
        database.todometerQueries.updateTaskState(
            id = id,
            state = state.toString()
        )
    }

    override suspend fun deleteTask(id: Long) = database.todometerQueries.deleteTask(id)
}
