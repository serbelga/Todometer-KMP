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

package dev.sergiobelda.todometer.common.compose.ui.addtask

import androidx.navigation.navDeepLink
import dev.sergiobelda.todometer.common.navigation.Action
import dev.sergiobelda.todometer.common.navigation.Destination
import dev.sergiobelda.todometer.common.navigation.NavigationParams

object AddTaskDestination : Destination {
    override val route: String = "addtask"

    private const val AddTaskDeepLink = "app://open.add.task"

    val addTaskNavDeepLink = navDeepLink { uriPattern = AddTaskDeepLink }
}

object AddTaskNavigationParams : NavigationParams(AddTaskDestination) {
    override val navigationRoute: String = destination.route
}

val Action.navigateToAddTask: () -> Unit
    get() = {
        navigate(AddTaskNavigationParams)
    }
