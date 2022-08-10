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

package dev.sergiobelda.todometer.wear.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.navArgument
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import dev.sergiobelda.todometer.wear.ui.Destinations.DeleteTask
import dev.sergiobelda.todometer.wear.ui.Destinations.DeleteTaskList
import dev.sergiobelda.todometer.wear.ui.Destinations.Home
import dev.sergiobelda.todometer.wear.ui.Destinations.TaskDetail
import dev.sergiobelda.todometer.wear.ui.Destinations.TaskDetailArgs.TaskId
import dev.sergiobelda.todometer.wear.ui.Destinations.TaskListTasks
import dev.sergiobelda.todometer.wear.ui.Destinations.TaskListTasksArgs.TaskListId
import dev.sergiobelda.todometer.wear.ui.deletetask.DeleteTaskScreen
import dev.sergiobelda.todometer.wear.ui.deletetasklist.DeleteTaskListScreen
import dev.sergiobelda.todometer.wear.ui.home.HomeScreen
import dev.sergiobelda.todometer.wear.ui.taskdetail.TaskDetailScreen
import dev.sergiobelda.todometer.wear.ui.tasklisttasks.TaskListTasksScreen
import dev.sergiobelda.todometer.wear.ui.theme.ToDometerTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ToDometerApp() {
    val navController = rememberSwipeDismissableNavController()
    val actions = remember(navController) { Actions(navController) }

    ToDometerTheme {
        SwipeDismissableNavHost(navController = navController, startDestination = Home) {
            composable(Home) {
                HomeScreen(
                    actions.navigateToTaskListTasks
                )
            }
            composable(
                "$TaskListTasks/{$TaskListId}",
                arguments = listOf(navArgument(TaskListId) { defaultValue = "" })
            ) { navBackStackEntry ->
                val taskListId = navBackStackEntry.arguments?.getString(TaskListId) ?: ""
                TaskListTasksScreen(
                    openTask = actions.navigateToTaskDetail,
                    deleteTaskList = { actions.navigateToDeleteTaskList(taskListId) },
                    taskListTasksViewModel = getViewModel { parametersOf(taskListId) }
                )
            }
            composable("$DeleteTaskList/{$TaskListId}") { navBackStackEntry ->
                val taskListId = navBackStackEntry.arguments?.getString(TaskListId) ?: ""
                DeleteTaskListScreen(
                    onDeleteTaskList = actions.popBackToHome,
                    navigateUp = actions.navigateUp,
                    deleteTaskListViewModel = getViewModel { parametersOf(taskListId) }
                )
            }
            composable("$TaskDetail/{$TaskId}") { navBackStackEntry ->
                val taskId = navBackStackEntry.arguments?.getString(TaskId) ?: ""
                TaskDetailScreen(
                    deleteTask = { actions.navigateToDeleteTask(taskId) },
                    taskDetailViewModel = getViewModel(parameters = { parametersOf(taskId) })
                )
            }
            composable("$DeleteTask/{$TaskId}") { navBackStackEntry ->
                val taskId = navBackStackEntry.arguments?.getString(TaskId) ?: ""
                DeleteTaskScreen(
                    onDeleteTask = actions.popBackToTaskListTasks,
                    navigateUp = actions.navigateUp,
                    deleteTaskViewModel = getViewModel(parameters = { parametersOf(taskId) })
                )
            }
        }
    }
}
