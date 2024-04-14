/*
 * Copyright 2024 Sergio Belda
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

package dev.sergiobelda.todometer.app.navhost

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.sergiobelda.todometer.app.feature.about.ui.AboutDestination
import dev.sergiobelda.todometer.app.feature.about.ui.AboutScreen
import dev.sergiobelda.todometer.app.feature.about.ui.navigateToAbout
import dev.sergiobelda.todometer.app.feature.addtask.ui.AddTaskDestination
import dev.sergiobelda.todometer.app.feature.addtask.ui.AddTaskScreen
import dev.sergiobelda.todometer.app.feature.addtask.ui.navigateToAddTask
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.AddTaskListDestination
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.AddTaskListScreen
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.navigateToAddTaskList
import dev.sergiobelda.todometer.app.feature.edittask.ui.EditTaskDestination
import dev.sergiobelda.todometer.app.feature.edittask.ui.EditTaskScreen
import dev.sergiobelda.todometer.app.feature.edittask.ui.navigateToEditTask
import dev.sergiobelda.todometer.app.feature.edittasklist.ui.EditTaskListDestination
import dev.sergiobelda.todometer.app.feature.edittasklist.ui.EditTaskListScreen
import dev.sergiobelda.todometer.app.feature.edittasklist.ui.navigateToEditTaskList
import dev.sergiobelda.todometer.app.feature.home.ui.HomeDestination
import dev.sergiobelda.todometer.app.feature.home.ui.HomeScreen
import dev.sergiobelda.todometer.app.feature.settings.ui.SettingsDestination
import dev.sergiobelda.todometer.app.feature.settings.ui.SettingsScreen
import dev.sergiobelda.todometer.app.feature.settings.ui.navigateToSettings
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.TaskDetailsDestination
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.TaskDetailsScreen
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.navigateToTaskDetails
import dev.sergiobelda.todometer.common.navigation.Action
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun TodometerNavHost(
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
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeNode(
            navigateToAddTaskList = action.navigateToAddTaskList,
            navigateToEditTaskList = action.navigateToEditTaskList,
            navigateToAddTask = action.navigateToAddTask,
            navigateToTaskDetails = action.navigateToTaskDetails,
            navigateToSettings = action.navigateToSettings,
            navigateToAbout = action.navigateToAbout
        )
        taskDetailsNode(
            navigateBack = navigateBackAction,
            navigateToEditTask = action.navigateToEditTask
        )
        addTaskListRoute(navigateBack = navigateBackAction)
        editTaskListNode(navigateBack = navigateBackAction)
        addTaskNode(navigateBack = navigateBackAction)
        editTaskNode(navigateBack = navigateBackAction)
        settingsNode(navigateBack = navigateBackAction)
        aboutNode(navigateBack = navigateBackAction)
    }
}

private fun NavGraphBuilder.homeNode(
    navigateToAddTaskList: () -> Unit,
    navigateToEditTaskList: () -> Unit,
    navigateToAddTask: () -> Unit,
    navigateToTaskDetails: (String) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToAbout: () -> Unit
) {
    composable(HomeDestination.route) {
        HomeScreen(
            navigateToAddTaskList = navigateToAddTaskList,
            navigateToEditTaskList = navigateToEditTaskList,
            navigateToAddTask = navigateToAddTask,
            navigateToTaskDetails = navigateToTaskDetails,
            navigateToSettings = navigateToSettings,
            navigateToAbout = navigateToAbout,
            viewModel = koinInject()
        )
    }
}

private fun NavGraphBuilder.taskDetailsNode(
    navigateBack: () -> Unit,
    navigateToEditTask: (String) -> Unit
) {
    composable(
        TaskDetailsDestination.route,
        deepLinks = listOf(TaskDetailsDestination.taskDetailNavDeepLink)
    ) { navBackStackEntry ->
        val taskId = TaskDetailsDestination.navArgsTaskId(navBackStackEntry)
        TaskDetailsScreen(
            navigateToEditTask = { navigateToEditTask(taskId) },
            navigateBack = navigateBack,
            viewModel = koinInject { parametersOf(taskId) }
        )
    }
}

private fun NavGraphBuilder.addTaskListRoute(
    navigateBack: () -> Unit
) {
    composable(AddTaskListDestination.route) {
        AddTaskListScreen(
            navigateBack = navigateBack,
            viewModel = koinInject()
        )
    }
}

private fun NavGraphBuilder.editTaskListNode(
    navigateBack: () -> Unit
) {
    composable(EditTaskListDestination.route) {
        EditTaskListScreen(
            navigateBack = navigateBack,
            viewModel = koinInject()
        )
    }
}

private fun NavGraphBuilder.addTaskNode(
    navigateBack: () -> Unit
) {
    composable(
        AddTaskDestination.route,
        deepLinks = listOf(AddTaskDestination.addTaskNavDeepLink)
    ) {
        AddTaskScreen(
            navigateBack = navigateBack,
            viewModel = koinInject()
        )
    }
}

private fun NavGraphBuilder.editTaskNode(
    navigateBack: () -> Unit
) {
    composable(EditTaskDestination.route) { backStackEntry ->
        val taskId = EditTaskDestination.navArgsTaskId(backStackEntry)
        EditTaskScreen(
            navigateBack = navigateBack,
            viewModel = koinInject { parametersOf(taskId) }
        )
    }
}

private fun NavGraphBuilder.settingsNode(
    navigateBack: () -> Unit
) {
    composable(SettingsDestination.route) {
        SettingsScreen(
            navigateBack = navigateBack,
            viewModel = koinInject()
        )
    }
}

private fun NavGraphBuilder.aboutNode(
    navigateBack: () -> Unit
) {
    composable(AboutDestination.route) {
        // TODO: Resolve commented code
        AboutScreen(
            navigateToGitHub = { /*context.openWebPage(GitHubUrl)*/ },
            navigateToPrivacyPolicy = { /*context.openWebPage(PrivacyPolicyUrl)*/ },
            navigateToOpenSourceLicenses = { /*context.launchActivity<OssLicensesMenuActivity>()*/ },
            navigateBack = navigateBack
        )
    }
}
