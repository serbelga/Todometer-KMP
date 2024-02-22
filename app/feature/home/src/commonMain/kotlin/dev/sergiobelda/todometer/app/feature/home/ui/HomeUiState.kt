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

package dev.sergiobelda.todometer.app.feature.home.ui

import androidx.compose.runtime.Immutable
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.ui.error.ErrorUi

@Immutable
data class HomeUiState(
    val isLoadingTasks: Boolean = false,
    val tasks: List<TaskItem> = emptyList(),
    val selectedTasks: List<String> = emptyList(),
    val taskLists: List<TaskList> = emptyList(),
    val taskListSelected: TaskList? = null,
    val errorUi: ErrorUi? = null
) {
    val selectionMode: Boolean get() = selectedTasks.isNotEmpty()

    val tasksDoingPinned: List<TaskItem> get() = tasks.filter { it.state == TaskState.DOING && it.isPinned }

    val tasksDoingNotPinned: List<TaskItem> get() = tasks.filter { it.state == TaskState.DOING && !it.isPinned }

    val tasksDone: List<TaskItem> get() = tasks.filter { it.state == TaskState.DONE }

    val isDefaultTaskListSelected: Boolean get() = taskListSelected == null
}
