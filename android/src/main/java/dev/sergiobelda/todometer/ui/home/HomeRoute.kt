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

package dev.sergiobelda.todometer.ui.home

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.compose.ui.home.HomeScreen
import org.koin.androidx.compose.getViewModel

@Composable
internal fun HomeRoute(
    navigateToAddTaskList: () -> Unit,
    navigateToEditTaskList: () -> Unit,
    navigateToAddTask: () -> Unit,
    navigateToTaskDetails: (String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit,
    homeViewModel: HomeViewModel = getViewModel()
) {
    HomeScreen(
        navigateToAddTaskList = navigateToAddTaskList,
        navigateToEditTaskList = navigateToEditTaskList,
        navigateToAddTask = navigateToAddTask,
        navigateToTaskDetails = navigateToTaskDetails,
        navigateToSettings = navigateToSettings,
        navigateToAbout = navigateToAbout,
        onTaskItemDoingClick = {
            homeViewModel.setTaskDoing(it)
        },
        onTaskItemDoneClick = {
            homeViewModel.setTaskDone(it)
        },
        onTaskListItemClick = {
            homeViewModel.setTaskListSelected(it)
        },
        onDeleteTasksClick = {
            homeViewModel.deleteSelectedTasks()
        },
        onDeleteTaskListClick = {
            homeViewModel.deleteTaskList()
        },
        onDeleteTask = {
            homeViewModel.deleteTask(it)
        },
        onClearSelectedTasks = {
            homeViewModel.clearSelectedTasks()
        },
        onSelectTaskItem = { homeViewModel.toggleSelectTask(it) },
        homeUiState = homeViewModel.homeUiState
    )
}
