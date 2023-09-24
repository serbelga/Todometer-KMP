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

package dev.sergiobelda.todometer.ui.taskdetails

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.ui.taskdetails.TaskDetailsScreen
import dev.sergiobelda.todometer.common.ui.taskdetails.TaskDetailsViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun TaskDetailsRoute(
    taskId: String,
    navigateToEditTask: () -> Unit,
    navigateBack: () -> Unit,
    taskDetailsViewModel: TaskDetailsViewModel = getViewModel { parametersOf(taskId) }
) {
    TaskDetailsScreen(
        navigateToEditTask = navigateToEditTask,
        navigateBack = navigateBack,
        taskDetailsUiState = taskDetailsViewModel.taskDetailsUiState,
        onTaskChecklistItemClick = { id, checked ->
            if (checked) {
                taskDetailsViewModel.setTaskChecklistItemChecked(id)
            } else {
                taskDetailsViewModel.setTaskChecklistItemUnchecked(id)
            }
        },
        onDeleteTaskCheckListItem = { id ->
            taskDetailsViewModel.deleteTaskChecklistItem(id)
        },
        onAddTaskCheckListItem = { text ->
            taskDetailsViewModel.insertTaskChecklistItem(text)
        }
    )
}
