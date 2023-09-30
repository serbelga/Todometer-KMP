/*
 * Copyright 2023 Sergio Belda
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

package dev.sergiobelda.todometer.app.common.ui.theme

import android.os.Build
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.sergiobelda.todometer.app.common.designsystem.theme.DarkColorScheme
import dev.sergiobelda.todometer.app.common.designsystem.theme.LightColorScheme
import dev.sergiobelda.todometer.app.common.designsystem.theme.TodometerDarkColors
import dev.sergiobelda.todometer.app.common.designsystem.theme.TodometerLightColors
import dev.sergiobelda.todometer.app.common.designsystem.theme.TodometerTheme
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_outline
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_outline

@Composable
actual fun TodometerAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val todometerColors = if (darkTheme) TodometerDarkColors else TodometerLightColors

    val colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        // Update default outline value for dynamic color schemes.
        if (darkTheme) {
            dynamicDarkColorScheme(context).copy(outline = todometer_dark_outline)
        } else {
            dynamicLightColorScheme(context).copy(outline = todometer_light_outline)
        }
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    TodometerTheme(
        todometerColors = todometerColors,
        colorScheme = colorScheme,
        content = content
    )
}
