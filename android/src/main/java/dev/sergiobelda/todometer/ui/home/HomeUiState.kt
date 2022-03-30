package dev.sergiobelda.todometer.ui.home

import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskList

data class HomeUiState(
    val isLoadingTasks: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val taskLists: List<TaskList> = emptyList(),
    val taskListSelected: TaskList? = null,
    val isDefaultTaskListSelected: Boolean = true,
    val errorMessage: String? = null
)
