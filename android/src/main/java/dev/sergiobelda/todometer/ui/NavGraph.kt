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
import dev.sergiobelda.todometer.ui.Destinations.AddProject
import dev.sergiobelda.todometer.ui.Destinations.AddTask
import dev.sergiobelda.todometer.ui.Destinations.EditProject
import dev.sergiobelda.todometer.ui.Destinations.EditTask
import dev.sergiobelda.todometer.ui.Destinations.TaskDetail

object Destinations {
    const val Home = "home"
    const val AddProject = "addProject"
    const val EditProject = "editProject"
    const val AddTask = "addTask"
    const val EditTask = "editTask"
    const val TaskDetail = "taskDetail"
    const val About = "about"

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
    val navigateToAddProject: () -> Unit = {
        navController.navigate(AddProject)
    }
    val navigateToEditProject: () -> Unit = {
        navController.navigate(EditProject)
    }
    val navigateToAbout: () -> Unit = {
        navController.navigate(About)
    }
    val navigateUp: () -> Unit = {
        navController.popBackStack()
    }
}
