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

package dev.sergiobelda.todometer.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.ui.Destinations.About
import dev.sergiobelda.todometer.ui.Destinations.AddTask
import dev.sergiobelda.todometer.ui.Destinations.AddTaskDeepLink
import dev.sergiobelda.todometer.ui.Destinations.AddTaskList
import dev.sergiobelda.todometer.ui.Destinations.EditTask
import dev.sergiobelda.todometer.ui.Destinations.EditTaskList
import dev.sergiobelda.todometer.ui.Destinations.Home
import dev.sergiobelda.todometer.ui.Destinations.TaskDetail
import dev.sergiobelda.todometer.ui.Destinations.TaskDetailArgs.TaskId
import dev.sergiobelda.todometer.ui.Destinations.TaskDetailDeepLink
import dev.sergiobelda.todometer.ui.about.AboutScreen
import dev.sergiobelda.todometer.ui.addtask.AddTaskScreen
import dev.sergiobelda.todometer.ui.addtasklist.AddTaskListScreen
import dev.sergiobelda.todometer.ui.edittask.EditTaskScreen
import dev.sergiobelda.todometer.ui.edittasklist.EditTaskListScreen
import dev.sergiobelda.todometer.ui.home.HomeScreen
import dev.sergiobelda.todometer.ui.taskdetail.TaskDetailScreen
import dev.sergiobelda.todometer.ui.theme.ToDometerTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ToDometerApp(mainViewModel: MainViewModel = getViewModel()) {
    val context = LocalContext.current

    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    val appThemeState = mainViewModel.appTheme.collectAsState()
    val darkTheme: Boolean = when (appThemeState.value) {
        AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        AppTheme.DARK_THEME -> true
        AppTheme.LIGHT_THEME -> false
    }
    ToDometerTheme(darkTheme) {
        NavHost(navController, startDestination = Home) {
            composable(Home) {
                HomeScreen(
                    addTaskList = actions.navigateToAddTaskList,
                    editTaskList = actions.navigateToEditTaskList,
                    addTask = actions.navigateToAddTask,
                    openTask = actions.navigateToTaskDetail,
                    openSourceLicenses = { startOpenSourceLicensesActivity(context) },
                    about = actions.navigateToAbout
                )
            }
            composable(
                "$TaskDetail/{$TaskId}",
                deepLinks = listOf(navDeepLink { uriPattern = "$TaskDetailDeepLink/{$TaskId}" })
            ) { navBackStackEntry ->
                val taskId = navBackStackEntry.arguments?.getString(TaskId) ?: ""
                TaskDetailScreen(
                    editTask = { actions.navigateToEditTask(taskId) },
                    navigateUp = actions.navigateUp,
                    taskDetailViewModel = getViewModel { parametersOf(taskId) }
                )
            }
            composable(AddTaskList) {
                AddTaskListScreen(actions.navigateUp)
            }
            composable(EditTaskList) {
                EditTaskListScreen(actions.navigateUp)
            }
            composable(
                AddTask,
                deepLinks = listOf(navDeepLink { uriPattern = AddTaskDeepLink })
            ) {
                AddTaskScreen(actions.navigateUp)
            }
            composable(
                "$EditTask/{$TaskId}"
            ) { backStackEntry ->
                val taskId = backStackEntry.arguments?.getString(TaskId) ?: ""
                EditTaskScreen(
                    navigateUp = actions.navigateUp,
                    editTaskViewModel = getViewModel { parametersOf(taskId) }
                )
            }
            composable(About) {
                AboutScreen(
                    openGithub = {
                        openWebPage(
                            context,
                            "https://github.com/serbelga/ToDometer_Multiplatform"
                        )
                    },
                    openSourceLicenses = { startOpenSourceLicensesActivity(context) },
                    navigateUp = actions.navigateUp
                )
            }
        }
    }
}

private fun startOpenSourceLicensesActivity(context: Context) {
    startActivity(
        context,
        Intent(context, OssLicensesMenuActivity::class.java),
        null
    )
}

private fun openWebPage(context: Context, url: String) {
    val webpage: Uri = Uri.parse(url)
    startActivity(
        context,
        Intent(Intent.ACTION_VIEW, webpage),
        null
    )
}
