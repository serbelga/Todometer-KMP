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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Immutable
data class HomeState(
    val isLoadingTasks: Boolean = false,
    val tasks: ImmutableList<TaskItem> = persistentListOf(),
    val selectedTasksIds: ImmutableList<String> = persistentListOf(),
    val taskLists: ImmutableList<TaskList> = persistentListOf(),
    val taskListSelected: TaskList? = null,
    val errorUi: ErrorUi? = null
) {
    val selectedTasks: ImmutableList<TaskItem>
        get() = tasks.filter { it.id in selectedTasksIds }.toPersistentList()

    val selectionMode: Boolean get() = selectedTasks.isNotEmpty()

    val tasksDoingPinned: ImmutableList<TaskItem>
        get() = tasks.filter { it.state == TaskState.DOING && it.isPinned }.toPersistentList()

    val tasksDoingNotPinned: ImmutableList<TaskItem>
        get() = tasks.filter { it.state == TaskState.DOING && !it.isPinned }.toPersistentList()

    val tasksDone: ImmutableList<TaskItem>
        get() = tasks.filter { it.state == TaskState.DONE }.toPersistentList()

    val isDefaultTaskListSelected: Boolean get() = taskListSelected == null
}
