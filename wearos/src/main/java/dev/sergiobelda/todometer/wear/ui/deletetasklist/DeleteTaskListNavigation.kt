package dev.sergiobelda.todometer.wear.ui.deletetasklist

import androidx.navigation.NavBackStackEntry
import dev.sergiobelda.todometer.common.android.navigation.Destination
import dev.sergiobelda.todometer.common.android.navigation.NavigationAction
import dev.sergiobelda.todometer.common.android.navigation.NavigationParams

object DeleteTaskListDestination : Destination {
    const val DeleteTaskList = "deletetasklist"
    private const val TaskListIdArg = "taskListId"

    override val route: String = "$DeleteTaskList/{$TaskListIdArg}"

    fun navArgsTaskListId(navBackStackEntry: NavBackStackEntry): String =
        navBackStackEntry.arguments?.getString(TaskListIdArg) ?: ""
}

class DeleteTaskListNavigationParams(taskListId: String) :
    NavigationParams(DeleteTaskListDestination) {

    override val navigationRoute: String = "${DeleteTaskListDestination.DeleteTaskList}/$taskListId"
}

val NavigationAction.navigateToDeleteTaskList: (String) -> Unit
    get() = { taskListId ->
        navigate(DeleteTaskListNavigationParams(taskListId))
    }
