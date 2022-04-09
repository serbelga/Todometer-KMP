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

package dev.sergiobelda.todometer.common.domain.usecase

import dev.sergiobelda.todometer.common.domain.Result
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.repository.ITaskListRepository
import dev.sergiobelda.todometer.common.domain.repository.IUserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetTaskListSelectedUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val taskListRepository: ITaskListRepository
) {

    /**
     * Retrieves the current task list selected. This flow emits a value of a task list
     * every time that current task list selected in user preferences changes or
     * has been updated in database.
     *
     * @return A Flow that emits the current task list selected.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Result<TaskList>> =
        userPreferencesRepository.taskListSelected().flatMapLatest { taskListId ->
            taskListRepository.getTaskList(taskListId)
        }
}
