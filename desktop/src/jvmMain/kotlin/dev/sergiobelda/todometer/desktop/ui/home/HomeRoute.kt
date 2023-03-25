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

package dev.sergiobelda.todometer.desktop.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.sergiobelda.todometer.common.compose.ui.home.HomeScreen
import dev.sergiobelda.todometer.desktop.koin
import org.koin.core.parameter.parametersOf

@Composable
fun HomeRoute(
    navigateToAddTaskList: () -> Unit,
    navigateToEditTaskList: () -> Unit,
    addTask: () -> Unit,
    openTask: (String) -> Unit,
    openSourceLicenses: () -> Unit,
    about: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = remember {
        koin.get { parametersOf(coroutineScope) }
    }

    val appTheme by homeViewModel.appTheme.collectAsState()
    HomeScreen(
        navigateToAddTaskList = navigateToAddTaskList,
        navigateToEditTaskList = navigateToEditTaskList,
        navigateToAddTask = addTask,
        onTaskItemClick = openTask,
        navigateToOpenSourceLicenses = openSourceLicenses,
        navigateToAbout = about,
        onTaskItemDoingClick = {
            homeViewModel.setTaskDoing(it)
        },
        onTaskItemDoneClick = {
            homeViewModel.setTaskDone(it)
        },
        onTaskListItemClick = {
            homeViewModel.setTaskListSelected(it)
        },
        onDeleteTaskClick = {
            homeViewModel.deleteTask(it)
        },
        onDeleteTaskListClick = {
            homeViewModel.deleteTaskList()
        },
        onChooseThemeClick = { homeViewModel.setAppTheme(it) },
        homeUiState = homeViewModel.homeUiState,
        appTheme = appTheme
    )
}
