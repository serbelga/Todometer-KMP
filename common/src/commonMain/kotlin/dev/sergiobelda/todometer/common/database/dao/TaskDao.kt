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

package dev.sergiobelda.todometer.common.database.dao

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import dev.sergiobelda.todometer.TaskEntity
import dev.sergiobelda.todometer.TodometerDatabase
import dev.sergiobelda.todometer.common.model.TaskState
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TaskDao : ITaskDao, KoinComponent {

    private val database: TodometerDatabase by inject()

    override fun getTask(id: String): Flow<TaskEntity> =
        database.todometerQueries.selectTask(id).asFlow().mapToOneNotNull()

    override fun getTasks(projectId: String): Flow<List<TaskEntity>> =
        database.todometerQueries.selectTasksByProjectId(projectId).asFlow().mapToList()

    override suspend fun insertTask(task: TaskEntity): String {
        database.todometerQueries.insertOrReplaceTask(
            id = task.id,
            title = task.title,
            description = task.description,
            state = task.state,
            tag = task.tag,
            project_id = task.project_id,
            sync = task.sync
        )
        // TODO Call return last_insert_rowid() from SQLDelight.
        return task.id
    }

    override suspend fun insertTasks(tasks: List<TaskEntity>) =
        tasks.forEach { task ->
            insertTask(task)
        }

    override suspend fun updateTask(task: TaskEntity) =
        database.todometerQueries.updateTask(
            id = task.id,
            title = task.title,
            description = task.description,
            tag = task.tag
        )

    override suspend fun updateTaskSync(id: String, sync: Boolean) {
        database.todometerQueries.updateTaskSync(
            id = id,
            sync = sync
        )
    }

    override suspend fun updateTaskState(id: String, state: TaskState) {
        database.todometerQueries.updateTaskState(
            id = id,
            state = state.toString()
        )
    }

    override suspend fun deleteTask(id: String) = database.todometerQueries.deleteTask(id)
}