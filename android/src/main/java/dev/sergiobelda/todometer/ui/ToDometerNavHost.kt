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

package dev.sergiobelda.todometer.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sergiobelda.todometer.common.android.extensions.launchActivity
import dev.sergiobelda.todometer.common.android.extensions.openWebPage
import dev.sergiobelda.todometer.common.compose.ui.addtask.AddTaskDestination
import dev.sergiobelda.todometer.common.compose.ui.addtask.navigateToAddTask
import dev.sergiobelda.todometer.common.compose.ui.addtasklist.AddTaskListDestination
import dev.sergiobelda.todometer.common.compose.ui.addtasklist.navigateToAddTaskList
import dev.sergiobelda.todometer.common.compose.ui.edittask.EditTaskDestination
import dev.sergiobelda.todometer.common.compose.ui.edittask.navigateToEditTask
import dev.sergiobelda.todometer.common.compose.ui.edittasklist.EditTaskListDestination
import dev.sergiobelda.todometer.common.compose.ui.edittasklist.navigateToEditTaskList
import dev.sergiobelda.todometer.common.compose.ui.home.HomeDestination
import dev.sergiobelda.todometer.common.compose.ui.settings.SettingsDestination
import dev.sergiobelda.todometer.common.compose.ui.settings.navigateToSettings
import dev.sergiobelda.todometer.common.compose.ui.taskdetails.TaskDetailsDestination
import dev.sergiobelda.todometer.common.compose.ui.taskdetails.navigateToTaskDetails
import dev.sergiobelda.todometer.common.navigation.Action
import dev.sergiobelda.todometer.ui.about.AboutDestination
import dev.sergiobelda.todometer.ui.about.AboutScreen
import dev.sergiobelda.todometer.ui.about.navigateToAbout
import dev.sergiobelda.todometer.ui.addtask.AddTaskRoute
import dev.sergiobelda.todometer.ui.addtasklist.AddTaskListRoute
import dev.sergiobelda.todometer.ui.edittask.EditTaskRoute
import dev.sergiobelda.todometer.ui.edittasklist.EditTaskListRoute
import dev.sergiobelda.todometer.ui.home.HomeRoute
import dev.sergiobelda.todometer.ui.settings.SettingsRoute
import dev.sergiobelda.todometer.ui.taskdetails.TaskDetailsRoute

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ToDometerNavHost(
    navController: NavHostController,
    action: Action,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val navigateBackAction: () -> Unit = {
        keyboardController?.hide()
        action.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(HomeDestination.route) {
            HomeRoute(
                navigateToAddTaskList = action.navigateToAddTaskList,
                navigateToEditTaskList = action.navigateToEditTaskList,
                navigateToAddTask = action.navigateToAddTask,
                navigateToTaskDetails = action.navigateToTaskDetails,
                navigateToSettings = action.navigateToSettings,
                navigateToAbout = action.navigateToAbout
            )
        }
        composable(
            TaskDetailsDestination.route,
            deepLinks = listOf(TaskDetailsDestination.taskDetailNavDeepLink)
        ) { navBackStackEntry ->
            val taskId = TaskDetailsDestination.navArgsTaskId(navBackStackEntry)
            TaskDetailsRoute(
                taskId = taskId,
                navigateToEditTask = { action.navigateToEditTask(taskId) },
                navigateBack = navigateBackAction
            )
        }
        composable(AddTaskListDestination.route) {
            AddTaskListRoute(navigateBack = navigateBackAction)
        }
        composable(EditTaskListDestination.route) {
            EditTaskListRoute(navigateBack = navigateBackAction)
        }
        composable(
            AddTaskDestination.route,
            deepLinks = listOf(AddTaskDestination.addTaskNavDeepLink)
        ) {
            AddTaskRoute(navigateBack = navigateBackAction)
        }
        composable(EditTaskDestination.route) { backStackEntry ->
            val taskId = EditTaskDestination.navArgsTaskId(backStackEntry)
            EditTaskRoute(taskId = taskId, navigateBack = navigateBackAction)
        }
        composable(SettingsDestination.route) {
            SettingsRoute(
                navigateBack = navigateBackAction
            )
        }
        composable(AboutDestination.route) {
            AboutScreen(
                navigateToGitHub = { context.openWebPage(GITHUB_URL) },
                navigateToOpenSourceLicenses = { context.launchActivity<OssLicensesMenuActivity>() },
                navigateBack = navigateBackAction
            )
        }
    }
}

private const val GITHUB_URL = "https://github.com/serbelga/ToDometer_Multiplatform"
