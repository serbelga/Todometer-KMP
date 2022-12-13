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

package dev.sergiobelda.todometer.desktop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.sergiobelda.todometer.common.core.di.initKoin
import dev.sergiobelda.todometer.desktop.navigation.NavController
import dev.sergiobelda.todometer.desktop.navigation.NavHost
import dev.sergiobelda.todometer.desktop.navigation.composableNode
import dev.sergiobelda.todometer.desktop.ui.home.HomeDestination
import dev.sergiobelda.todometer.desktop.ui.home.HomeScreen
import dev.sergiobelda.todometer.desktop.ui.icons.iconToDometer
import dev.sergiobelda.todometer.desktop.ui.task.TaskDetailDestination
import dev.sergiobelda.todometer.desktop.ui.task.TaskDetailScreen
import dev.sergiobelda.todometer.desktop.ui.theme.ToDometerAppTheme

val koin = initKoin().koin

fun main() = application {
    Window(
        resizable = false,
        onCloseRequest = ::exitApplication,
        title = "ToDometer",
        state = WindowState(
            size = DpSize(600.dp, 800.dp),
            position = WindowPosition.Aligned(Alignment.Center)
        ),
        icon = iconToDometer()
    ) {
        val navController by remember { mutableStateOf(NavController()) }
        ToDometerAppTheme {
            NavHost(navController, startDestination = HomeDestination.route) {
                composableNode(destinationId = HomeDestination.route) {
                    HomeScreen {
                        navController.navigateTo(TaskDetailDestination.route)
                    }
                }
                composableNode(destinationId = TaskDetailDestination.route) {
                    TaskDetailScreen {
                        navController.navigateTo(HomeDestination.route)
                    }
                }
            }
        }
    }
}
