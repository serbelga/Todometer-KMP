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

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

typealias ComposeMaterialTypography = androidx.compose.material.Typography

val DarkColorPalette = darkColors(
    primary = primaryDark,
    primaryVariant = orange,
    secondary = green,
    background = navy,
    surface = navy,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

val LightColorPalette = lightColors(
    primary = primaryLight,
    primaryVariant = orange,
    secondary = green,
    background = background,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

val Colors.outline: Color
    @Composable get() = if (isLight) outlineLight else outlineDark

val TodometerColors: Colors
    @Composable get() = MaterialTheme.colors

val TodometerShapes: Shapes
    @Composable get() = MaterialTheme.shapes

val TodometerTypography: ComposeMaterialTypography
    @Composable get() = MaterialTheme.typography

val Colors.pink: Color
    @Composable get() = if (isLight) pinkLight else pinkDark

val Colors.red: Color
    @Composable get() = if (isLight) redLight else redDark

val Colors.blue: Color
    @Composable get() = if (isLight) blueLight else blueDark

val Colors.indigo: Color
    @Composable get() = if (isLight) indigoLight else indigoDark

val Colors.teal: Color
    @Composable get() = if (isLight) tealLight else tealDark

val Colors.green: Color
    @Composable get() = if (isLight) greenLight else greenDark

val Colors.lime: Color
    @Composable get() = if (isLight) limeLight else limeDark

val Colors.yellow: Color
    @Composable get() = if (isLight) yellowLight else yellowDark

val Colors.amber: Color
    @Composable get() = if (isLight) amberLight else amberDark

val Colors.orange: Color
    @Composable get() = if (isLight) orangeLight else orangeDark

val Colors.brown: Color
    @Composable get() = if (isLight) brownLight else brownDark

val Colors.gray: Color
    @Composable get() = if (isLight) grayLight else grayDark
