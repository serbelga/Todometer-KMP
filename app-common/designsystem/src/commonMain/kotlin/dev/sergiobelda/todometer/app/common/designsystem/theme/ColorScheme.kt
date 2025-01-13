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

package dev.sergiobelda.todometer.app.common.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import dev.sergiobelda.todometer.common.designsystem.resources.color.amberDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.amberLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.blueDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.blueLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.brownDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.brownLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.grayDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.grayLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.greenDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.greenLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.indigoDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.indigoLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.limeDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.limeLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.orangeDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.orangeLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.pinkDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.pinkLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.redDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.redLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.tealDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.tealLight
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_background
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_error
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_errorContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_inverseOnSurface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_inversePrimary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_inverseSurface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onBackground
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onError
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onErrorContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onPrimary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onPrimaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onSecondary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onSecondaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onSurface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onSurfaceVariant
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onTertiary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onTertiaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_outline
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_primary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_primaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_secondary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_secondaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_surface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_surfaceTint
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_surfaceVariant
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_tertiary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_tertiaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_background
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_error
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_errorContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_inverseOnSurface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_inversePrimary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_inverseSurface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onBackground
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onError
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onErrorContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onPrimary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onPrimaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onSecondary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onSecondaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onSurface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onSurfaceVariant
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onTertiary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_onTertiaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_outline
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_primary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_primaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_secondary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_secondaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_surface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_surfaceTint
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_surfaceVariant
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_tertiary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_light_tertiaryContainer
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_secondary50
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_secondary80
import dev.sergiobelda.todometer.common.designsystem.resources.color.yellowDark
import dev.sergiobelda.todometer.common.designsystem.resources.color.yellowLight

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
    surfaceTint = todometer_light_surfaceTint,
)

val TodometerLightColors = todometerLightColors(
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
    yellow = yellowLight,
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
    surfaceTint = todometer_dark_surfaceTint,
)

val TodometerDarkColors = todometerDarkColors(
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
    yellow = yellowDark,
)
