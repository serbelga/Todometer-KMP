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

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkColorPalette = darkColorScheme(
    primary = primaryDark,
    secondary = green,
    background = navy,
    surface = navy,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

val LightColorPalette = lightColorScheme(
    primary = primaryLight,
    secondary = green,
    background = background,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

val ColorScheme.outline: Color
    @Composable get() = if (isLight) outlineLight else outlineDark

val TodometerColors: ColorScheme
    @Composable get() = MaterialTheme.colorScheme

val TodometerShapes: Shapes
    @Composable get() = MaterialTheme.shapes

val TodometerTypography: ComposeMaterialTypography
    @Composable get() = MaterialTheme.typography

val ColorScheme.pink: Color
    @Composable get() = if (isLight) pinkLight else pinkDark

val ColorScheme.red: Color
    @Composable get() = if (isLight) redLight else redDark

val ColorScheme.blue: Color
    @Composable get() = if (isLight) blueLight else blueDark

val ColorScheme.indigo: Color
    @Composable get() = if (isLight) indigoLight else indigoDark

val ColorScheme.teal: Color
    @Composable get() = if (isLight) tealLight else tealDark

val ColorScheme.green: Color
    @Composable get() = if (isLight) greenLight else greenDark

val ColorScheme.lime: Color
    @Composable get() = if (isLight) limeLight else limeDark

val ColorScheme.yellow: Color
    @Composable get() = if (isLight) yellowLight else yellowDark

val ColorScheme.amber: Color
    @Composable get() = if (isLight) amberLight else amberDark

val ColorScheme.orange: Color
    @Composable get() = if (isLight) orangeLight else orangeDark

val ColorScheme.brown: Color
    @Composable get() = if (isLight) brownLight else brownDark

val ColorScheme.gray: Color
    @Composable get() = if (isLight) grayLight else grayDark
