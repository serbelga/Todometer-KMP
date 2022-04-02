package dev.sergiobelda.todometer.wear.ui.tasklisttasks

import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskList

data class TaskListTasksUiState(
    val isLoadingTaskList: Boolean = false,
    val taskList: TaskList? = null,
    val isDefaultTaskList: Boolean = false,
    val isLoadingTasks: Boolean = false,
    val tasks: List<Task> = emptyList()
)
