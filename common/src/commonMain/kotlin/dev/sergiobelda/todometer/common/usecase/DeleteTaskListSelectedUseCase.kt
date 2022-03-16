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

import dev.sergiobelda.todometer.common.data.doIfError
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.repository.ITaskListRepository
import dev.sergiobelda.todometer.common.repository.IUserPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull

class DeleteTaskListSelectedUseCase(
    private val userPreferencesRepository: IUserPreferencesRepository,
    private val taskListRepository: ITaskListRepository
) {

    /**
     * Deletes a task list. The deleted task list will be the current task list selected.
     * Once is deleted, it will select the first task list in task list list if is not empty.
     */
    suspend operator fun invoke() {
        val taskListId = userPreferencesRepository.taskListSelected().firstOrNull()
        taskListId?.let { taskListRepository.deleteTaskList(it) }
        val taskLists = taskListRepository.getTaskLists().firstOrNull()
        taskLists?.doIfSuccess { list ->
            list.firstOrNull()?.let { taskList ->
                userPreferencesRepository.setTaskListSelected(taskList.id)
            } ?: userPreferencesRepository.setTaskListSelected("")
        }?.doIfError {
            userPreferencesRepository.setTaskListSelected("")
        }
    }
}
