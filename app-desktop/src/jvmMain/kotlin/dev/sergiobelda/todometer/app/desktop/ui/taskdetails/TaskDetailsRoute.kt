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

package dev.sergiobelda.todometer.app.desktop.ui.taskdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.sergiobelda.todometer.app.common.ui.viewmodel.subscribeToComposition
import dev.sergiobelda.todometer.app.desktop.koin
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.TaskDetailsScreen
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.TaskDetailsViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun TaskDetailsRoute(
    taskId: String,
    navigateToEditTask: () -> Unit,
    navigateBack: () -> Unit,
    taskDetailsViewModel: TaskDetailsViewModel = remember { koin.get { parametersOf(taskId) } }
) {
    taskDetailsViewModel.subscribeToComposition()
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
