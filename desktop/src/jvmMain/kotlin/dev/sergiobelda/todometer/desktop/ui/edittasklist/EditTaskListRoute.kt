/*
 * Copyright 2023 Sergio Belda
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

package dev.sergiobelda.todometer.desktop.ui.edittasklist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.sergiobelda.todometer.common.compose.ui.edittasklist.EditTaskListScreen
import dev.sergiobelda.todometer.common.compose.ui.edittasklist.EditTaskListViewModel
import dev.sergiobelda.todometer.desktop.koin
import dev.sergiobelda.todometer.desktop.ui.viewmodel.clearDisposableEffect

@Composable
internal fun EditTaskListRoute(navigateBack: () -> Unit) {
    val editTaskListViewModel: EditTaskListViewModel = remember { koin.get() }
    editTaskListViewModel.clearDisposableEffect()
    EditTaskListScreen(
        navigateBack = navigateBack,
        editTaskList = { taskListName ->
            editTaskListViewModel.updateTaskList(taskListName)
        },
        editTaskListUiState = editTaskListViewModel.editTaskListUiState
    )
}
