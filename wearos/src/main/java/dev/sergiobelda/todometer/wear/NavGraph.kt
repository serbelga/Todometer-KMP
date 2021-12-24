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

package dev.sergiobelda.todometer.wear

import androidx.navigation.NavHostController
import dev.sergiobelda.todometer.wear.Destinations.AddTask
import dev.sergiobelda.todometer.wear.Destinations.AddTaskList
import dev.sergiobelda.todometer.wear.Destinations.DeleteTask
import dev.sergiobelda.todometer.wear.Destinations.DeleteTaskList
import dev.sergiobelda.todometer.wear.Destinations.EditTask
import dev.sergiobelda.todometer.wear.Destinations.EditTaskList
import dev.sergiobelda.todometer.wear.Destinations.Home
import dev.sergiobelda.todometer.wear.Destinations.TaskDetail
import dev.sergiobelda.todometer.wear.Destinations.TaskListTasks

object Destinations {
    const val Home = "home"
    const val AddTaskList = "addTaskList"
    const val TaskListTasks = "taskListTasks"
    const val AddTask = "addTask"
    const val TaskDetail = "taskDetail"
    const val EditTask = "editTask"
    const val DeleteTask = "deleteTask"
    const val EditTaskList = "editTaskList"
    const val DeleteTaskList = "deleteTaskList"

    object TaskListTasksArgs {
        const val TaskListId = "taskListId"
    }

    object TaskDetailArgs {
        const val TaskId = "taskId"
    }
}

class Actions(navController: NavHostController) {
    val navigateToTaskListTasks: (String) -> Unit = { taskListId ->
        navController.navigate("$TaskListTasks/$taskListId")
    }
    val navigateToAddTaskList: () -> Unit = { navController.navigate(AddTaskList) }
    val navigateToAddTask: (String) -> Unit = { taskListId ->
        navController.navigate("$AddTask/$taskListId")
    }
    val navigateToTaskDetail: (String) -> Unit = { taskId ->
        navController.navigate("$TaskDetail/$taskId")
    }
    val navigateToEditTask: (String) -> Unit = { taskId ->
        navController.navigate("$EditTask/$taskId")
    }
    val navigateToDeleteTask: (String) -> Unit = { taskId ->
        navController.navigate("$DeleteTask/$taskId")
    }
    val popBackToTaskListTasks: () -> Unit = {
        navController.popBackStack(TaskListTasks, true)
    }
    val navigateToEditTaskList: (String) -> Unit = { taskId ->
        navController.navigate("$EditTaskList/$taskId")
    }
    val navigateToDeleteTaskList: (String) -> Unit = { taskId ->
        navController.navigate("$DeleteTaskList/$taskId")
    }
    val popBackToHome: () -> Unit = {
        navController.popBackStack(Home, false)
    }
    val navigateUp: () -> Unit = { navController.popBackStack() }
}
