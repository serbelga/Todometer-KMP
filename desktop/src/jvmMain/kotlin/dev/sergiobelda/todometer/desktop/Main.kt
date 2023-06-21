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

package dev.sergiobelda.todometer.desktop

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.sergiobelda.todometer.common.compose.ui.about.AboutDestination
import dev.sergiobelda.todometer.common.compose.ui.addtask.AddTaskDestination
import dev.sergiobelda.todometer.common.compose.ui.addtasklist.AddTaskListDestination
import dev.sergiobelda.todometer.common.compose.ui.edittask.EditTaskDestination
import dev.sergiobelda.todometer.common.compose.ui.edittasklist.EditTaskListDestination
import dev.sergiobelda.todometer.common.compose.ui.settings.SettingsDestination
import dev.sergiobelda.todometer.common.compose.ui.taskdetails.TaskDetailsDestination
import dev.sergiobelda.todometer.common.core.di.startAppDI
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.domain.usecase.apptheme.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.navigation.NavigationController
import dev.sergiobelda.todometer.common.navigation.NavigationHost
import dev.sergiobelda.todometer.common.navigation.composableNode
import dev.sergiobelda.todometer.common.resources.ToDometerSymbols
import dev.sergiobelda.todometer.desktop.ui.about.AboutRoute
import dev.sergiobelda.todometer.desktop.ui.addtask.AddTaskRoute
import dev.sergiobelda.todometer.desktop.ui.addtasklist.AddTaskListRoute
import dev.sergiobelda.todometer.desktop.ui.edittask.EditTaskRoute
import dev.sergiobelda.todometer.desktop.ui.edittasklist.EditTaskListRoute
import dev.sergiobelda.todometer.desktop.ui.home.HomeDestination
import dev.sergiobelda.todometer.desktop.ui.home.HomeRoute
import dev.sergiobelda.todometer.desktop.ui.settings.SettingsRoute
import dev.sergiobelda.todometer.desktop.ui.taskdetails.TaskDetailsRoute
import dev.sergiobelda.todometer.desktop.ui.theme.ToDometerAppTheme

val koin = startAppDI().koin

fun main() = application {
    Window(
        resizable = false,
        onCloseRequest = ::exitApplication,
        title = "ToDometer",
        state = WindowState(
            size = DpSize(600.dp, 800.dp),
            position = WindowPosition.Aligned(Alignment.Center)
        ),
        icon = ToDometerSymbols.IsotypeMonochrome
    ) {
        val getAppThemeUseCase = koin.get<GetAppThemeUseCase>()
        val appThemeState = getAppThemeUseCase().collectAsState(AppTheme.FOLLOW_SYSTEM)
        val darkTheme: Boolean = when (appThemeState.value) {
            AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            AppTheme.DARK_THEME -> true
            AppTheme.LIGHT_THEME -> false
        }
        val navigationController by remember { mutableStateOf(NavigationController()) }
        ToDometerAppTheme(darkTheme) {
            NavigationHost(navigationController, startDestination = HomeDestination.route) {
                composableNode(destinationId = HomeDestination.route) {
                    HomeRoute(
                        navigateToAddTaskList = {
                            navigationController.navigateTo(AddTaskListDestination.route)
                        },
                        navigateToEditTaskList = {
                            navigationController.navigateTo(EditTaskListDestination.route)
                        },
                        navigateToAddTask = {
                            navigationController.navigateTo(AddTaskDestination.route)
                        },
                        navigateToTaskDetails = { taskId ->
                            navigationController.navigateTo(
                                TaskDetailsDestination.route,
                                TaskDetailsDestination.TaskIdArg to taskId
                            )
                        },
                        navigateToSettings = {
                            navigationController.navigateTo(SettingsDestination.route)
                        },
                        navigateToAbout = {
                            navigationController.navigateTo(AboutDestination.route)
                        }
                    )
                }
                composableNode(destinationId = TaskDetailsDestination.route) {
                    val taskId =
                        navigationController.getStringArgOrNull(TaskDetailsDestination.TaskIdArg)
                            ?: ""
                    TaskDetailsRoute(
                        taskId = taskId,
                        navigateToEditTask = {
                            navigationController.navigateTo(
                                EditTaskDestination.route,
                                EditTaskDestination.TaskIdArg to taskId
                            )
                        },
                        navigateBack = { navigationController.navigateTo(HomeDestination.route) }
                    )
                }
                composableNode(destinationId = AddTaskListDestination.route) {
                    AddTaskListRoute(
                        navigateBack = { navigationController.navigateTo(HomeDestination.route) }
                    )
                }
                composableNode(destinationId = EditTaskListDestination.route) {
                    EditTaskListRoute(
                        navigateBack = { navigationController.navigateTo(HomeDestination.route) }
                    )
                }
                composableNode(destinationId = AddTaskDestination.route) {
                    AddTaskRoute(
                        navigateBack = { navigationController.navigateTo(HomeDestination.route) }
                    )
                }
                composableNode(destinationId = EditTaskDestination.route) {
                    val taskId =
                        navigationController.getStringArgOrNull(EditTaskDestination.TaskIdArg)
                            ?: ""
                    EditTaskRoute(
                        taskId = taskId,
                        navigateBack = {
                            navigationController.navigateTo(
                                TaskDetailsDestination.route,
                                TaskDetailsDestination.TaskIdArg to taskId
                            )
                        }
                    )
                }
                composableNode(destinationId = SettingsDestination.route) {
                    SettingsRoute(
                        navigateBack = { navigationController.navigateTo(HomeDestination.route) }
                    )
                }
                composableNode(destinationId = AboutDestination.route) {
                    AboutRoute(
                        navigateBack = { navigationController.navigateTo(HomeDestination.route) }
                    )
                }
            }
        }
    }
}
