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

package dev.sergiobelda.todometer.common.data.repository

import dev.sergiobelda.todometer.common.data.localdatasource.ITaskChecklistItemLocalDataSource
import dev.sergiobelda.todometer.common.data.util.randomUUIDString
import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItem
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import dev.sergiobelda.todometer.common.domain.repository.ITaskChecklistItemsRepository
import kotlinx.coroutines.flow.Flow

class TaskChecklistItemsRepository(
    private val taskChecklistItemLocalDataSource: ITaskChecklistItemLocalDataSource
) : ITaskChecklistItemsRepository {

    override fun getTaskChecklistItems(taskId: String): Flow<Result<List<TaskChecklistItem>>> =
        taskChecklistItemLocalDataSource.getTaskChecklistItems(taskId)

    override suspend fun insertTaskChecklistItems(taskId: String, vararg items: String) {
        taskChecklistItemLocalDataSource.insertTaskChecklistItems(
            *items.map { text ->
                TaskChecklistItem(
                    id = randomUUIDString(),
                    text = text,
                    state = TaskChecklistItemState.UNCHECKED,
                    taskId = taskId
                )
            }.toTypedArray()
        )
    }

    override suspend fun updateTaskChecklistItemState(id: String, state: TaskChecklistItemState) =
        taskChecklistItemLocalDataSource.updateTaskChecklistItemState(id, state)

    override suspend fun deleteTaskChecklistItem(id: String) =
        taskChecklistItemLocalDataSource.deleteTaskChecklistItem(id)
}
