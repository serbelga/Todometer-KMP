/*
 * Copyright 2022 Sergio Belda
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

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.sergiobelda.todometer.common.database.TaskChecklistItemEntity
import dev.sergiobelda.todometer.common.database.TodometerDatabase
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TaskChecklistItemDao(private val todometerDatabase: TodometerDatabase) :
    ITaskChecklistItemDao {

    override fun getTaskChecklistItems(taskId: String): Flow<List<TaskChecklistItemEntity>> =
        todometerDatabase.taskChecklistItemEntityQueries.selectTaskChecklistItems(taskId).asFlow()
            .mapToList(Dispatchers.Default)

    override suspend fun insertTaskChecklistItems(vararg taskChecklistItemEntities: TaskChecklistItemEntity) =
        todometerDatabase.taskChecklistItemEntityQueries.transaction {
            taskChecklistItemEntities.forEach { taskChecklistItemEntity ->
                todometerDatabase.taskChecklistItemEntityQueries.insertTaskChecklistItem(
                    id = taskChecklistItemEntity.id,
                    text = taskChecklistItemEntity.text,
                    task_id = taskChecklistItemEntity.task_id,
                    state = taskChecklistItemEntity.state,
                )
            }
        }

    override suspend fun updateTaskChecklistItemState(id: String, state: TaskChecklistItemState) {
        todometerDatabase.taskChecklistItemEntityQueries.updateTaskChecklistItemState(
            id = id,
            state = state,
        )
    }

    override suspend fun deleteTaskChecklistItem(id: String) {
        todometerDatabase.taskChecklistItemEntityQueries.deleteTaskChecklistItem(id)
    }
}
