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

package dev.sergiobelda.todometer.wearapp.wearos.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.sergiobelda.fonament.di.koin.koinFonamentViewModel
import dev.sergiobelda.fonament.navigation.NavigationNode
import dev.sergiobelda.navigation.compose.extended.NavAction
import dev.sergiobelda.navigation.compose.extended.wear.SwipeDismissableNavHost
import dev.sergiobelda.navigation.compose.extended.wear.composable
import dev.sergiobelda.todometer.wearapp.wearos.ui.home.HomeNavDestination
import dev.sergiobelda.todometer.wearapp.wearos.ui.home.HomeScreen
import dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetails.TaskDetailsNavDestination
import dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetails.TaskDetailsSafeNavArgs
import dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetails.TaskDetailsScreen
import dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetails.navigation.taskDetailsNavigationEventHandler
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.TaskListTasksNavDestination
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.TaskListTasksSafeNavArgs
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.TaskListTasksScreen
import org.koin.core.parameter.parametersOf

@Composable
fun TodometerNavHost(
    navController: NavHostController,
    navAction: NavAction,
) {
    SwipeDismissableNavHost(
        navController = navController,
        startNavDestination = HomeNavDestination,
    ) {
        composable(navDestination = HomeNavDestination) {
            HomeScreen(
                openTaskList = { taskListId ->
                    navAction.navigate(
                        TaskListTasksNavDestination.safeNavRoute(taskListId),
                    )
                },
            )
        }
        composable(navDestination = TaskListTasksNavDestination) { navBackStackEntry ->
            val taskListId = TaskListTasksSafeNavArgs(navBackStackEntry).taskListId.orEmpty()
            TaskListTasksScreen(
                taskListId = taskListId,
                openTask = { taskId ->
                    navAction.navigate(
                        TaskDetailsNavDestination.safeNavRoute(taskId),
                    )
                },
                navigateBack = { navAction.popBackStack() },
            )
        }
        composable(navDestination = TaskDetailsNavDestination) { navBackStackEntry ->
            val taskId = TaskDetailsSafeNavArgs(navBackStackEntry).taskId
            TaskDetailsScreen.NavigationNode(
                viewModel = koinFonamentViewModel { parametersOf(taskId) },
                navigationEventHandler = taskDetailsNavigationEventHandler(
                    navigateBack = { navAction.popBackStack() },
                ),
            )
        }
    }
}
