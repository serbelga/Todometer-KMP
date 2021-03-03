/*
 * Copyright 2020 Sergio Belda
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

package com.sergiobelda.todometer.ui

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.sergiobelda.todometer.ui.Destinations.AddProject
import com.sergiobelda.todometer.ui.Destinations.AddTask
import com.sergiobelda.todometer.ui.Destinations.EditTask
import com.sergiobelda.todometer.ui.Destinations.ProjectDetail
import com.sergiobelda.todometer.ui.Destinations.TaskDetail

object Destinations {
    const val Home = "home"
    const val AddProject = "addProject"
    const val AddTask = "addTask"
    const val EditTask = "editTask"
    const val ProjectDetail = "projectDetail"
    const val TaskDetail = "taskDetail"

    object ProjectDetailArgs {
        const val ProjectId = "projectId"
    }
    object TaskDetailArgs {
        const val TaskId = "taskId"
    }
}

class Actions(navController: NavHostController) {
    val openProject: (Int) -> Unit = { projectId ->
        navController.navigate("$ProjectDetail/$projectId")
    }
    val openTask: (Int) -> Unit = { taskId ->
        navController.navigate("$TaskDetail/$taskId")
    }
    val addTask: () -> Unit = {
        navController.navigate(AddTask)
    }
    val editTask: (Int) -> Unit = { taskId ->
        navController.navigate("$EditTask/$taskId")
    }
    val addProject: () -> Unit = {
        navController.navigate(AddProject)
    }
    val navigateUp: () -> Unit = {
        navController.popBackStack()
    }
}
