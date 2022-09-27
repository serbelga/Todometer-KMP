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

val LightColorScheme = lightColorScheme(
    primary = todometer_light_primary,
    onPrimary = todometer_light_onPrimary,
    primaryContainer = todometer_light_primaryContainer,
    onPrimaryContainer = todometer_light_onPrimaryContainer,
    secondary = todometer_light_secondary,
    onSecondary = todometer_light_onSecondary,
    secondaryContainer = todometer_light_secondaryContainer,
    onSecondaryContainer = todometer_light_onSecondaryContainer,
    tertiary = todometer_light_tertiary,
    onTertiary = todometer_light_onTertiary,
    tertiaryContainer = todometer_light_tertiaryContainer,
    onTertiaryContainer = todometer_light_onTertiaryContainer,
    error = todometer_light_error,
    errorContainer = todometer_light_errorContainer,
    onError = todometer_light_onError,
    onErrorContainer = todometer_light_onErrorContainer,
    background = todometer_light_background,
    onBackground = todometer_light_onBackground,
    surface = todometer_light_surface,
    onSurface = todometer_light_onSurface,
    surfaceVariant = todometer_light_surfaceVariant,
    onSurfaceVariant = todometer_light_onSurfaceVariant,
    outline = todometer_light_outline,
    inverseOnSurface = todometer_light_inverseOnSurface,
    inverseSurface = todometer_light_inverseSurface,
    inversePrimary = todometer_light_inversePrimary,
    surfaceTint = todometer_light_surfaceTint
)

val ToDometerLightColors = toDometerLightColors(
    onSurfaceMediumEmphasis = todometer_light_onSurface.copy(alpha = Alpha.Medium),
    check = todometer_secondary50,
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
    primary = todometer_dark_primary,
    onPrimary = todometer_dark_onPrimary,
    primaryContainer = todometer_dark_primaryContainer,
    onPrimaryContainer = todometer_dark_onPrimaryContainer,
    secondary = todometer_dark_secondary,
    onSecondary = todometer_dark_onSecondary,
    secondaryContainer = todometer_dark_secondaryContainer,
    onSecondaryContainer = todometer_dark_onSecondaryContainer,
    tertiary = todometer_dark_tertiary,
    onTertiary = todometer_dark_onTertiary,
    tertiaryContainer = todometer_dark_tertiaryContainer,
    onTertiaryContainer = todometer_dark_onTertiaryContainer,
    error = todometer_dark_error,
    errorContainer = todometer_dark_errorContainer,
    onError = todometer_dark_onError,
    onErrorContainer = todometer_dark_onErrorContainer,
    background = todometer_dark_background,
    onBackground = todometer_dark_onBackground,
    surface = todometer_dark_surface,
    onSurface = todometer_dark_onSurface,
    surfaceVariant = todometer_dark_surfaceVariant,
    onSurfaceVariant = todometer_dark_onSurfaceVariant,
    outline = todometer_dark_outline,
    inverseOnSurface = todometer_dark_inverseOnSurface,
    inverseSurface = todometer_dark_inverseSurface,
    inversePrimary = todometer_dark_inversePrimary,
    surfaceTint = todometer_dark_surfaceTint
)

val ToDometerDarkColors = toDometerDarkColors(
    onSurfaceMediumEmphasis = todometer_dark_onSurface.copy(alpha = Alpha.Medium),
    check = todometer_secondary80,
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
