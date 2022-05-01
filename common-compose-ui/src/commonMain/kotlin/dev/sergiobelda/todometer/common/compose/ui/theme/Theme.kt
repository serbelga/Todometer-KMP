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

package dev.sergiobelda.todometer.common.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

typealias ComposeMaterialTypography = androidx.compose.material3.Typography

val DarkColorPalette = lightColorScheme(
    primary = primaryDark,
    secondary = green,
    background = darkSurface,
    surface = darkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    error = red300
)

val LightColorPalette = lightColorScheme(
    primary = primaryLight,
    secondary = green,
    background = background,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = red800
)

val ColorScheme.outline: Color
    @Composable get() = if (isSystemInDarkTheme()) outlineLight else outlineDark

val ColorScheme.onSurfaceMediumEmphasis: Color
    @Composable get() = onSurface.copy(alpha = 0.6f)

val ColorScheme.onSurfaceDisabled: Color
    @Composable get() = onSurface.copy(alpha = 0.38f)

val TodometerColors: ColorScheme
    @Composable get() = MaterialTheme.colorScheme

val TodometerTypography: ComposeMaterialTypography
    @Composable get() = MaterialTheme.typography

val ColorScheme.pink: Color
    @Composable get() = if (isSystemInDarkTheme()) pinkLight else pinkDark

val ColorScheme.red: Color
    @Composable get() = if (isSystemInDarkTheme()) redLight else redDark

val ColorScheme.blue: Color
    @Composable get() = if (isSystemInDarkTheme()) blueLight else blueDark

val ColorScheme.indigo: Color
    @Composable get() = if (isSystemInDarkTheme()) indigoLight else indigoDark

val ColorScheme.teal: Color
    @Composable get() = if (isSystemInDarkTheme()) tealLight else tealDark

val ColorScheme.green: Color
    @Composable get() = if (isSystemInDarkTheme()) greenLight else greenDark

val ColorScheme.lime: Color
    @Composable get() = if (isSystemInDarkTheme()) limeLight else limeDark

val ColorScheme.yellow: Color
    @Composable get() = if (isSystemInDarkTheme()) yellowLight else yellowDark

val ColorScheme.amber: Color
    @Composable get() = if (isSystemInDarkTheme()) amberLight else amberDark

val ColorScheme.orange: Color
    @Composable get() = if (isSystemInDarkTheme()) orangeLight else orangeDark

val ColorScheme.brown: Color
    @Composable get() = if (isSystemInDarkTheme()) brownLight else brownDark

val ColorScheme.gray: Color
    @Composable get() = if (isSystemInDarkTheme()) grayLight else grayDark
