package dev.sergiobelda.todometer.app

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.sergiobelda.todometer.app.feature.about.ui.AboutDestination
import dev.sergiobelda.todometer.app.feature.about.ui.navigateToAbout
import dev.sergiobelda.todometer.app.feature.addtask.ui.AddTaskDestination
import dev.sergiobelda.todometer.app.feature.addtask.ui.navigateToAddTask
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.AddTaskListDestination
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.navigateToAddTaskList
import dev.sergiobelda.todometer.app.feature.edittask.ui.EditTaskDestination
import dev.sergiobelda.todometer.app.feature.edittask.ui.navigateToEditTask
import dev.sergiobelda.todometer.app.feature.edittasklist.ui.EditTaskListDestination
import dev.sergiobelda.todometer.app.feature.edittasklist.ui.navigateToEditTaskList
import dev.sergiobelda.todometer.app.feature.home.ui.HomeDestination
import dev.sergiobelda.todometer.app.feature.settings.ui.SettingsDestination
import dev.sergiobelda.todometer.app.feature.settings.ui.navigateToSettings
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.TaskDetailsDestination
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.navigateToTaskDetails
import dev.sergiobelda.todometer.app.ui.about.AboutRoute
import dev.sergiobelda.todometer.app.ui.addtask.AddTaskRoute
import dev.sergiobelda.todometer.app.ui.addtasklist.AddTaskListRoute
import dev.sergiobelda.todometer.app.ui.edittask.EditTaskRoute
import dev.sergiobelda.todometer.app.ui.edittasklist.EditTaskListRoute
import dev.sergiobelda.todometer.app.ui.home.HomeRoute
import dev.sergiobelda.todometer.app.ui.settings.SettingsRoute
import dev.sergiobelda.todometer.app.ui.taskdetails.TaskDetailsRoute
import dev.sergiobelda.todometer.common.navigation.Action

@Composable
fun TodometerNavHost(
    navController: NavHostController,
    action: Action,
    modifier: Modifier = Modifier,
    // TODO: Remove this extra parameters
    navigateBackAction: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
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
            // TODO: Resolve commented code
            AboutRoute(
                navigateToGitHub = { /*context.openWebPage(GitHubUrl)*/ },
                navigateToPrivacyPolicy = { /*context.openWebPage(PrivacyPolicyUrl)*/ },
                navigateToOpenSourceLicenses = { /*context.launchActivity<OssLicensesMenuActivity>()*/ },
                navigateBack = navigateBackAction
            )
        }
    }
}
