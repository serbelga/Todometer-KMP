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

package dev.sergiobelda.todometer.common.usecase

import dev.sergiobelda.todometer.common.data.Result
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.repository.ITaskRepository
import dev.sergiobelda.todometer.common.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetTaskListSelectedTasksUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val taskRepository: ITaskRepository
) {

    /**
     * Get the list of [Task] for the current task list selected. This flow emits a new value
     * every time that current task list selected in user preferences changes or
     * some task has been updated.
     */
    operator fun invoke(): Flow<Result<List<Task>>> =
        userPreferencesRepository.taskListSelected().flatMapLatest { taskListId ->
            taskRepository.getTasks(taskListId)
        }
}
