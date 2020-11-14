/*
 * Copyright 2020 Sergio Belda
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

package com.sergiobelda.todometer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.sergiobelda.todometer.ui.addproject.AddProjectScreen
import com.sergiobelda.todometer.ui.addtask.AddTaskScreen
import com.sergiobelda.todometer.ui.home.HomeScreen
import com.sergiobelda.todometer.ui.taskdetail.TaskDetailScreen
import com.sergiobelda.todometer.ui.theme.ToDometerTheme
import com.sergiobelda.todometer.viewmodel.MainViewModel

@Composable
fun ToDometerApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    ToDometerTheme {
        NavHost(navController = navController, startDestination = HOME_ROUTE) {
            composable(HOME_ROUTE) {
                HomeScreen(
                    mainViewModel = mainViewModel,
                    addTask = actions.addTask,
                    addProject = actions.addProject,
                    openTask = actions.openTask
                )
            }
            composable(
                TASK_DETAIL_ROUTE,
                arguments = listOf(navArgument(TASK_ID_ARG) { type = NavType.IntType })
            ) { backStackEntry ->
                TaskDetailScreen(
                    taskId = backStackEntry.arguments?.getInt(TASK_ID_ARG) ?: 0,
                    mainViewModel = mainViewModel,
                    navigateUp = actions.navigateUp
                )
            }
            composable(ADD_PROJECT_ROUTE) {
                AddProjectScreen(
                    mainViewModel = mainViewModel,
                    navigateUp = actions.navigateUp
                )
            }
            composable(ADD_TASK_ROUTE) {
                AddTaskScreen(
                    mainViewModel = mainViewModel,
                    navigateUp = actions.navigateUp
                )
            }
        }
    }
}
