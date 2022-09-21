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

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sergiobelda.todometer.navigation.Action
import dev.sergiobelda.todometer.navigation.openWebPage
import dev.sergiobelda.todometer.ui.about.AboutDestination
import dev.sergiobelda.todometer.ui.about.AboutScreen
import dev.sergiobelda.todometer.ui.about.navigateToAbout
import dev.sergiobelda.todometer.ui.addtask.AddTaskDestination
import dev.sergiobelda.todometer.ui.addtask.AddTaskScreen
import dev.sergiobelda.todometer.ui.addtask.navigateToAddTask
import dev.sergiobelda.todometer.ui.addtasklist.AddTaskListDestination
import dev.sergiobelda.todometer.ui.addtasklist.AddTaskListScreen
import dev.sergiobelda.todometer.ui.addtasklist.navigateToAddTaskList
import dev.sergiobelda.todometer.ui.edittask.EditTaskDestination
import dev.sergiobelda.todometer.ui.edittask.EditTaskScreen
import dev.sergiobelda.todometer.ui.edittask.navigateToEditTask
import dev.sergiobelda.todometer.ui.edittasklist.EditTaskListDestination
import dev.sergiobelda.todometer.ui.edittasklist.EditTaskListScreen
import dev.sergiobelda.todometer.ui.edittasklist.navigateToEditTaskList
import dev.sergiobelda.todometer.ui.home.HomeDestination
import dev.sergiobelda.todometer.ui.home.HomeScreen
import dev.sergiobelda.todometer.ui.taskdetail.TaskDetailDestination
import dev.sergiobelda.todometer.ui.taskdetail.TaskDetailScreen
import dev.sergiobelda.todometer.ui.taskdetail.navigateToTaskDetail

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToDometerNavHost(
    context: Context,
    navController: NavHostController,
    action: Action,
    modifier: Modifier = Modifier
) {
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
            HomeScreen(
                addTaskList = action.navigateToAddTaskList,
                editTaskList = action.navigateToEditTaskList,
                addTask = action.navigateToAddTask,
                openTask = action.navigateToTaskDetail,
                openSourceLicenses = { startOpenSourceLicensesActivity(context) },
                about = action.navigateToAbout
            )
        }
        composable(
            TaskDetailDestination.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = TaskDetailDestination.uriPattern }
            )
        ) { navBackStackEntry ->
            val taskId = TaskDetailDestination.navArgsTaskId(navBackStackEntry)
            TaskDetailScreen(
                taskId = taskId,
                editTask = { action.navigateToEditTask(taskId) },
                navigateUp = navigateBackAction
            )
        }
        composable(AddTaskListDestination.route) {
            AddTaskListScreen(navigateBackAction)
        }
        composable(EditTaskListDestination.route) {
            EditTaskListScreen(navigateBackAction)
        }
        composable(
            AddTaskDestination.route,
            deepLinks = listOf(navDeepLink { uriPattern = AddTaskDestination.uriPattern })
        ) {
            AddTaskScreen(navigateBackAction)
        }
        composable(EditTaskDestination.route) { backStackEntry ->
            val taskId = EditTaskDestination.navArgsTaskId(backStackEntry)
            EditTaskScreen(taskId = taskId, navigateUp = navigateBackAction)
        }
        composable(AboutDestination.route) {
            AboutScreen(
                openGithub = {
                    openWebPage(
                        context,
                        "https://github.com/serbelga/ToDometer_Multiplatform"
                    )
                },
                openSourceLicenses = { startOpenSourceLicensesActivity(context) },
                navigateUp = navigateBackAction
            )
        }
    }
}

private fun startOpenSourceLicensesActivity(context: Context) {
    ContextCompat.startActivity(
        context,
        Intent(context, OssLicensesMenuActivity::class.java),
        null
    )
}