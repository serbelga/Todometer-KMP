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

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import dev.sergiobelda.todometer.common.di.initKoin
import ui.home.HomeScreen
import ui.icons.iconToDometer
import ui.theme.ToDometerTheme

val koin = initKoin().koin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ToDometer",
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center)
        ),
        icon = iconToDometer()
    ) {
        var currentPage: Screen by remember { mutableStateOf(Screen.Home) }
        val navigateToHome: () -> Unit = {
            currentPage = Screen.Home
        }
        ToDometerTheme(darkTheme = false) {
            Crossfade(currentPage) { screen ->
                when (screen) {
                    Screen.Home -> HomeScreen()
                    else -> {
                    }
                }
            }
        }
    }
}
