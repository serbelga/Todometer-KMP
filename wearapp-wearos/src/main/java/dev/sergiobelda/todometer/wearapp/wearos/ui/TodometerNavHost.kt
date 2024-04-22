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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import dev.sergiobelda.navigation.compose.extended.NavAction
import dev.sergiobelda.todometer.wearapp.wearos.ui.home.HomeNavDestination
import dev.sergiobelda.todometer.wearapp.wearos.ui.home.HomeScreen
import dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetail.TaskDetailNavDestination
import dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetail.TaskDetailSafeNavArgs
import dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetail.TaskDetailScreen
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.TaskListTasksNavArgumentKeys
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.TaskListTasksNavDestination
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.TaskListTasksSafeNavArgs
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.TaskListTasksScreen

@Composable
fun TodometerNavHost(
    navController: NavHostController,
    navAction: NavAction,
    modifier: Modifier = Modifier
) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = HomeNavDestination.route,
        modifier = modifier
    ) {
        composable(HomeNavDestination.route) {
            HomeScreen(
                openTaskList = { taskListId ->
                    navAction.navigate(
                        TaskListTasksNavDestination.navRoute(
                            TaskListTasksNavArgumentKeys.TaskListIdNavArgumentKey to taskListId
                        )
                    )
                }
            )
        }
        composable(
            TaskListTasksNavDestination.route,
            arguments = TaskListTasksNavDestination.arguments
        ) { navBackStackEntry ->
            val taskListId = TaskListTasksSafeNavArgs(navBackStackEntry).taskListId.orEmpty()
            TaskListTasksScreen(
                taskListId = taskListId,
                openTask = { taskId ->
                    navAction.navigate(
                        TaskDetailNavDestination.safeNavRoute(taskId)
                    )
                },
                navigateBack = { navAction.popBackStack() }
            )
        }
        composable(
            TaskDetailNavDestination.route,
            arguments = TaskDetailNavDestination.arguments,
            deepLinks = TaskDetailNavDestination.deepLinks
        ) { navBackStackEntry ->
            val taskId = TaskDetailSafeNavArgs(navBackStackEntry).taskId.orEmpty()
            TaskDetailScreen(
                taskId = taskId,
                navigateBack = { navAction.popBackStack() }
            )
        }
    }
}
