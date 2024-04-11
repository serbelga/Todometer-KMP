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

package dev.sergiobelda.todometer.app.desktop.ui.addtasklist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.sergiobelda.todometer.app.common.ui.viewmodel.subscribeToComposition
import dev.sergiobelda.todometer.app.desktop.koin
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.AddTaskListScreen
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.AddTaskListViewModel

@Composable
internal fun AddTaskListRoute(
    navigateBack: () -> Unit,
    addTaskListViewModel: AddTaskListViewModel = remember { koin.get() }
) {
    addTaskListViewModel.subscribeToComposition()
    AddTaskListScreen(
        navigateBack = navigateBack,
        insertTaskList = { taskListName ->
            addTaskListViewModel.insertTaskList(taskListName)
        },
        addTaskListUiState = addTaskListViewModel.addTaskListUiState
    )
}
