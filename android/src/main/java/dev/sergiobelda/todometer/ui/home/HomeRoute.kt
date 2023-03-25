package dev.sergiobelda.todometer.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.sergiobelda.todometer.common.compose.ui.home.HomeScreen
import dev.sergiobelda.todometer.glance.ToDometerWidgetReceiver
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeRoute(
    navigateToAddTaskList: () -> Unit,
    navigateToEditTaskList: () -> Unit,
    addTask: () -> Unit,
    openTask: (String) -> Unit,
    openSourceLicenses: () -> Unit,
    about: () -> Unit,
    homeViewModel: HomeViewModel = getViewModel()
) {
    val appTheme by homeViewModel.appTheme.collectAsStateWithLifecycle()
    HomeScreen(
        navigateToAddTaskList = navigateToAddTaskList,
        navigateToEditTaskList = navigateToEditTaskList,
        navigateToAddTask = addTask,
        onTaskItemClick = openTask,
        navigateToOpenSourceLicenses = openSourceLicenses,
        navigateToAbout = about,
        onTaskItemDoingClick = {
            homeViewModel.setTaskDoing(it)
            updateToDometerWidgetData()
        },
        onTaskItemDoneClick = {
            homeViewModel.setTaskDone(it)
            updateToDometerWidgetData()
        },
        onTaskListItemClick = {
            homeViewModel.setTaskListSelected(it)
            updateToDometerWidgetData()
        },
        onDeleteTaskClick = {
            homeViewModel.deleteTask(it)
            updateToDometerWidgetData()
        },
        onDeleteTaskListClick = {
            homeViewModel.deleteTaskList()
            updateToDometerWidgetData()
        },
        onChooseThemeClick = { homeViewModel.setAppTheme(it) },
        homeUiState = homeViewModel.homeUiState,
        appTheme = appTheme
    )
}

private fun updateToDometerWidgetData() {
    ToDometerWidgetReceiver().updateData()
}
