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

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    secondary = green,
    background = background,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = red800,
    outline = outlineLight
)

val ToDometerLightColors = toDometerLightColors(
    onSurfaceMediumEmphasis = Color.Black.copy(alpha = 0.6f),
    amber = amberLight,
    blue = blueLight,
    brown = brownLight,
    gray = grayLight,
    green = greenLight,
    indigo = indigoLight,
    lime = limeLight,
    orange = orangeLight,
    red = redLight,
    pink = pinkLight,
    teal = tealLight,
    yellow = yellowLight
)

val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = green,
    background = darkSurface,
    surface = darkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    error = red300,
    outline = outlineDark
)

val ToDometerDarkColors = toDometerDarkColors(
    onSurfaceMediumEmphasis = Color.White.copy(alpha = 0.6f),
    amber = amberDark,
    blue = blueDark,
    brown = brownDark,
    gray = grayDark,
    green = greenDark,
    indigo = indigoDark,
    lime = limeDark,
    orange = orangeDark,
    red = redDark,
    pink = pinkDark,
    teal = tealDark,
    yellow = yellowDark
)
