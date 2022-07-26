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

package dev.sergiobelda.todometer.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.sergiobelda.todometer.common.compose.ui.theme.DarkColorScheme
import dev.sergiobelda.todometer.common.compose.ui.theme.LightColorScheme
import dev.sergiobelda.todometer.common.compose.ui.theme.ToDometerDarkColors
import dev.sergiobelda.todometer.common.compose.ui.theme.ToDometerLightColors
import dev.sergiobelda.todometer.common.compose.ui.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.compose.ui.theme.Type.typography
import dev.sergiobelda.todometer.common.compose.ui.theme.shapes

@Composable
fun ToDometerAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val toDometerColors = if (darkTheme) ToDometerDarkColors else ToDometerLightColors

    val colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else {
        if (darkTheme) DarkColorScheme else LightColorScheme
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = colorScheme.surface,
            darkIcons = !darkTheme
        )
    }

    ToDometerTheme(
        toDometerColors = toDometerColors,
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
