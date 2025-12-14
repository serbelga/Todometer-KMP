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

package dev.sergiobelda.todometer.common.fake.data.repository

import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.domain.repository.ITaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskRepository : ITaskRepository {
    override fun getTask(id: String): Flow<Result<Task>> = flow {}

    override fun getTasks(taskListId: String): Flow<Result<List<TaskItem>>> =
        flow {
            emit(
                Result.Success(
                    List(10) {
                        TaskItem(
                            id = it.toString(),
                            title = "The task title",
                            tag = Tag.UNSPECIFIED,
                            dueDate = 0,
                            state = TaskState.DOING,
                            taskListId = "0",
                            isPinned = false,
                            sync = false,
                            checklistItemsDone = 0,
                            totalChecklistItems = 0,
                        )
                    },
                ),
            )
        }

    override suspend fun insertTask(
        title: String,
        tag: Tag,
        description: String?,
        dueDate: Long?,
        taskListId: String,
    ): Result<String> = Result.Success("")

    override suspend fun updateTask(task: Task) = Unit

    override suspend fun updateTaskState(
        id: String,
        state: TaskState,
    ) = Unit

    override suspend fun deleteTasks(vararg ids: String) = Unit

    override suspend fun toggleTaskPinnedValue(id: String) = Unit
}
