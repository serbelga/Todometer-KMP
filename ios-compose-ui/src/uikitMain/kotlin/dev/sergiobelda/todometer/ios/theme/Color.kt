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

package dev.sergiobelda.todometer.ios.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.todometer_dark_onPrimary
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.todometer_dark_onSecondary
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.todometer_dark_primary
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.todometer_dark_secondary
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.todometer_light_onPrimary
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.todometer_light_onSecondary
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.todometer_light_primary
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.todometer_light_secondary

// TODO: Remove this
val LightColorPalette = lightColors(
    primary = todometer_light_primary,
    onPrimary = todometer_light_onPrimary,
    secondary = todometer_light_secondary,
    onSecondary = todometer_light_onSecondary
)

// TODO: Remove this
val DarkColorPalette = darkColors(
    primary = todometer_dark_primary,
    onPrimary = todometer_dark_onPrimary,
    secondary = todometer_dark_secondary,
    onSecondary = todometer_dark_onSecondary
)
