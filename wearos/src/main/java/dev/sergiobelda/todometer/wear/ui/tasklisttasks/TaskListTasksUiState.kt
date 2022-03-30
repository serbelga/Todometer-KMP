package dev.sergiobelda.todometer.wear.ui.tasklisttasks

import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskList

data class TaskListTasksUiState(
    val taskList: TaskList? = null,
    val isLoadingTasks: Boolean = false,
    val tasks: List<Task> = emptyList()
)
