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

package com.sergiobelda.todometer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.sergiobelda.todometer.ui.Destinations.AddProject
import com.sergiobelda.todometer.ui.Destinations.AddTask
import com.sergiobelda.todometer.ui.Destinations.EditTask
import com.sergiobelda.todometer.ui.Destinations.Home
import com.sergiobelda.todometer.ui.Destinations.ProjectDetail
import com.sergiobelda.todometer.ui.Destinations.ProjectDetailArgs.ProjectId
import com.sergiobelda.todometer.ui.Destinations.TaskDetail
import com.sergiobelda.todometer.ui.Destinations.TaskDetailArgs.TaskId
import com.sergiobelda.todometer.ui.addproject.AddProjectScreen
import com.sergiobelda.todometer.ui.home.HomeScreen
import com.sergiobelda.todometer.ui.project.ProjectDetailScreen
import com.sergiobelda.todometer.ui.task.AddTaskScreen
import com.sergiobelda.todometer.ui.task.EditTaskScreen
import com.sergiobelda.todometer.ui.task.TaskDetailScreen
import com.sergiobelda.todometer.ui.theme.ToDometerTheme
import com.sergiobelda.todometer.viewmodel.MainViewModel

@Composable
fun ToDometerApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    ToDometerTheme {
        NavHost(navController, startDestination = Home) {
            composable(Home) {
                HomeScreen(mainViewModel, actions.addProject, actions.addTask, actions.openProject, actions.openTask)
            }
            composable(
                "$ProjectDetail/{$ProjectId}",
                arguments = listOf(navArgument(ProjectId) { type = NavType.IntType })
            ) { navBackStackEntry ->
                ProjectDetailScreen(
                    projectId = navBackStackEntry.arguments?.getInt(ProjectId) ?: 0,
                    mainViewModel,
                    navigateUp = actions.navigateUp
                )
            }
            composable(
                "$TaskDetail/{$TaskId}",
                arguments = listOf(navArgument(TaskId) { type = NavType.IntType })
            ) { navBackStackEntry ->
                TaskDetailScreen(
                    taskId = navBackStackEntry.arguments?.getInt(TaskId) ?: 0,
                    mainViewModel,
                    actions.editTask,
                    actions.navigateUp
                )
            }
            composable(AddProject) {
                AddProjectScreen(mainViewModel, actions.navigateUp)
            }
            composable(AddTask) {
                AddTaskScreen(mainViewModel, actions.navigateUp)
            }
            composable(
                "$EditTask/{$TaskId}",
                arguments = listOf(navArgument(TaskId) { type = NavType.IntType })
            ) { backStackEntry ->
                EditTaskScreen(
                    taskId = backStackEntry.arguments?.getInt(TaskId) ?: 0,
                    mainViewModel,
                    actions.navigateUp
                )
            }
        }
    }
}