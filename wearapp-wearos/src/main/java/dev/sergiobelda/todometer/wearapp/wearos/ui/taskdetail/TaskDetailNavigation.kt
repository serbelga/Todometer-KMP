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

package dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetail

import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import dev.sergiobelda.navigation.compose.extended.NavArgumentKey
import dev.sergiobelda.navigation.compose.extended.NavDestination

enum class TaskDetailNavArgumentKeys(override val argumentKey: String) : NavArgumentKey {
    TaskIdNavArgumentKey("taskId")
}

object TaskDetailNavDestination : NavDestination<TaskDetailNavArgumentKeys>() {
    override val destinationId: String = "taskdetail"

    override val argumentsMap: Map<TaskDetailNavArgumentKeys, NavArgumentBuilder.() -> Unit> = mapOf(
        TaskDetailNavArgumentKeys.TaskIdNavArgumentKey to {
            type = NavType.StringType
        }
    )

    override val deepLinkUris: List<String> = listOf(
        "app://open.task"
    )
}
