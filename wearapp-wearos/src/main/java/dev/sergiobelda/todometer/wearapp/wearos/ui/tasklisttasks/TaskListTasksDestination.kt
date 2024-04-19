/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks

import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import dev.sergiobelda.navigation.compose.extended.NavArgumentKey
import dev.sergiobelda.navigation.compose.extended.NavDestination

enum class TaskListTasksNavArgumentKeys(override val argumentKey: String) : NavArgumentKey {
    TaskListIdNavArgumentKey("taskListId")
}

object TaskListTasksNavDestination : NavDestination<TaskListTasksNavArgumentKeys>() {
    override val destinationId: String = "tasklisttasks"

    override val argumentsMap: Map<TaskListTasksNavArgumentKeys, NavArgumentBuilder.() -> Unit> = mapOf(
        TaskListTasksNavArgumentKeys.TaskListIdNavArgumentKey to {
            type = NavType.StringType
            nullable = true
        }
    )
}
