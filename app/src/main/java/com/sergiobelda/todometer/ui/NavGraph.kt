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

const val HOME_ROUTE = "home"
const val ADD_PROJECT_ROUTE = "addProject"
const val ADD_TASK_ROUTE = "addTask"
const val TASK_DETAIL = "taskDetail"
const val TASK_ID_ARG = "taskId"
const val TASK_DETAIL_ROUTE = "$TASK_DETAIL/{$TASK_ID_ARG}"

class Actions(navController: NavHostController) {
    val openTask: (Int) -> Unit = { taskId ->
        navController.navigate("$TASK_DETAIL/$taskId")
    }
    val addTask: () -> Unit = {
        navController.navigate(ADD_TASK_ROUTE)
    }
    val addProject: () -> Unit = {
        navController.navigate(ADD_PROJECT_ROUTE)
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}
