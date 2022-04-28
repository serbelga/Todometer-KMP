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

package dev.sergiobelda.todometer.ui

import androidx.navigation.NavHostController
import dev.sergiobelda.todometer.ui.Destinations.About
import dev.sergiobelda.todometer.ui.Destinations.AddTask
import dev.sergiobelda.todometer.ui.Destinations.AddTaskList
import dev.sergiobelda.todometer.ui.Destinations.EditTask
import dev.sergiobelda.todometer.ui.Destinations.EditTaskList
import dev.sergiobelda.todometer.ui.Destinations.TaskDetail

object Destinations {
    const val Home = "home"
    const val AddTaskList = "addTaskList"
    const val EditTaskList = "editTaskList"
    const val AddTask = "addTask"
    const val EditTask = "editTask"
    const val TaskDetail = "taskDetail"
    const val About = "about"

    const val AddTaskDeepLink = "app://open.add.task"
    const val TaskDetailDeepLink = "app://open.task"

    object TaskDetailArgs {
        const val TaskId = "taskId"
    }
}

class Actions(navController: NavHostController) {
    val navigateToTaskDetail: (String) -> Unit = { taskId ->
        navController.navigate("$TaskDetail/$taskId")
    }
    val navigateToAddTask: () -> Unit = {
        navController.navigate(AddTask)
    }
    val navigateToEditTask: (String) -> Unit = { taskId ->
        navController.navigate("$EditTask/$taskId")
    }
    val navigateToAddTaskList: () -> Unit = {
        navController.navigate(AddTaskList)
    }
    val navigateToEditTaskList: () -> Unit = {
        navController.navigate(EditTaskList)
    }
    val navigateToAbout: () -> Unit = {
        navController.navigate(About)
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}
