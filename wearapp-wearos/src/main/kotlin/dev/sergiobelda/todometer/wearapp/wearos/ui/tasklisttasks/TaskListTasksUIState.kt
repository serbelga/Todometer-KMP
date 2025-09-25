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

package dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks

import androidx.compose.runtime.Immutable
import dev.sergiobelda.fonament.presentation.ui.FonamentUIState
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.ui.error.ErrorUi
import dev.sergiobelda.todometer.common.ui.task.TaskProgress
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class TaskListTasksUIState(
    val taskListUIState: TaskListUIState,
    val tasksUIState: TasksUIState,
) : FonamentUIState

sealed interface TaskListUIState {

    data object Loading : TaskListUIState

    @Immutable
    data class Success(
        val taskList: TaskList,
    ) : TaskListUIState

    data object DefaultTaskList : TaskListUIState

    data class Error(
        val errorUi: ErrorUi?,
    ) : TaskListUIState
}

sealed interface TasksUIState {

    data object Loading : TasksUIState

    @Immutable
    data class Success(
        val tasks: ImmutableList<TaskItem>,
    ) : TasksUIState {

        val progress
            get() = TaskProgress.getTasksDoneProgress(tasks)
    }

    data class Error(
        val errorUi: ErrorUi?,
    ) : TasksUIState
}
