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

package dev.sergiobelda.todometer.common.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalTodometerColors = staticCompositionLocalOf { todometerLightColors() }

object TodometerTheme {
    val todometerColors: TodometerColors
        @Composable
        @ReadOnlyComposable
        get() = LocalTodometerColors.current
}

@Composable
fun TodometerTheme(
    todometerColors: TodometerColors = TodometerTheme.todometerColors,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    shapes: Shapes = MaterialTheme.shapes,
    typography: Typography = MaterialTheme.typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalTodometerColors provides todometerColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }
}
