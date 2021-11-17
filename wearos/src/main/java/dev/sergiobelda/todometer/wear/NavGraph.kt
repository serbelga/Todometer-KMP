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
import dev.sergiobelda.todometer.wear.Destinations.AddProject
import dev.sergiobelda.todometer.wear.Destinations.AddTask
import dev.sergiobelda.todometer.wear.Destinations.ProjectTasks

object Destinations {
    const val Home = "home"
    const val AddProject = "addProject"
    const val ProjectTasks = "projectTasks"
    const val AddTask = "addTask"

    object ProjectTasksArgs {
        const val ProjectId = "projectId"
    }
}

class Actions(navController: NavHostController) {
    val openProject: (String) -> Unit = { projectId ->
        navController.navigate("$ProjectTasks/$projectId")
    }
    val addProject: () -> Unit = { navController.navigate(AddProject) }
    val addTask: () -> Unit = { navController.navigate(AddTask) }
}
