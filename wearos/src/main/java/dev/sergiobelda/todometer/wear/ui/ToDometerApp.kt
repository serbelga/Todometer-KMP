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
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import dev.sergiobelda.todometer.wear.Actions
import dev.sergiobelda.todometer.wear.Destinations.AddTask
import dev.sergiobelda.todometer.wear.Destinations.AddTaskList
import dev.sergiobelda.todometer.wear.Destinations.DeleteTaskList
import dev.sergiobelda.todometer.wear.Destinations.EditTaskList
import dev.sergiobelda.todometer.wear.Destinations.Home
import dev.sergiobelda.todometer.wear.Destinations.TaskDetail
import dev.sergiobelda.todometer.wear.Destinations.TaskDetailArgs.TaskId
import dev.sergiobelda.todometer.wear.Destinations.TaskListTasks
import dev.sergiobelda.todometer.wear.Destinations.TaskListTasksArgs.TaskListId
import dev.sergiobelda.todometer.wear.ui.addtask.AddTaskScreen
import dev.sergiobelda.todometer.wear.ui.addtasklist.AddTaskListScreen
import dev.sergiobelda.todometer.wear.ui.deletetasklist.DeleteTaskListScreen
import dev.sergiobelda.todometer.wear.ui.edittasklist.EditTaskListScreen
import dev.sergiobelda.todometer.wear.ui.home.HomeScreen
import dev.sergiobelda.todometer.wear.ui.taskdetail.TaskDetailScreen
import dev.sergiobelda.todometer.wear.ui.tasklisttasks.TaskListTasksScreen
import dev.sergiobelda.todometer.wear.ui.theme.ToDometerTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ToDometerApp() {
    val navController = rememberSwipeDismissableNavController()
    val actions = remember(navController) { Actions(navController) }

    ToDometerTheme {
        SwipeDismissableNavHost(navController = navController, startDestination = Home) {
            composable(Home) {
                HomeScreen(
                    actions.navigateToAddTaskList,
                    actions.navigateToTaskListTasks
                )
            }
            composable(AddTaskList) {
                AddTaskListScreen(actions.navigateUp)
            }
            composable("$TaskListTasks/{$TaskListId}") { navBackStackEntry ->
                val taskListId = navBackStackEntry.arguments?.getString(TaskListId) ?: ""
                TaskListTasksScreen(
                    addTask = { actions.navigateToAddTask(taskListId) },
                    openTask = actions.navigateToTaskDetail,
                    editTaskList = { actions.navigateToEditTaskList(taskListId) },
                    deleteTaskList = { actions.navigateToDeleteTaskList(taskListId) },
                    taskListTasksViewModel = getViewModel { parametersOf(taskListId) }
                )
            }
            composable("$AddTask/{$TaskListId}") { navBackStackEntry ->
                val taskListId = navBackStackEntry.arguments?.getString(TaskListId) ?: ""
                AddTaskScreen(
                    actions.navigateUp,
                    addTaskViewModel = getViewModel { parametersOf(taskListId) }
                )
            }
            composable("$EditTaskList/{$TaskListId}") { navBackStackEntry ->
                val taskListId = navBackStackEntry.arguments?.getString(TaskListId) ?: ""
                EditTaskListScreen(
                    actions.navigateUp,
                    editTaskListViewModel = getViewModel { parametersOf(taskListId) }
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
                    editTask = {},
                    removeTask = {},
                    taskDetailViewModel = getViewModel(parameters = { parametersOf(taskId) })
                )
            }
        }
    }
}
