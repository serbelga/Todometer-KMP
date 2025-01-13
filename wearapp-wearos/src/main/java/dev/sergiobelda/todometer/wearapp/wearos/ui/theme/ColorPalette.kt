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

package dev.sergiobelda.todometer.wearapp.wearos.ui.theme

import androidx.wear.compose.material.Colors
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_error
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onError
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onPrimary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onSecondary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_onSurface
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_primary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_secondary
import dev.sergiobelda.todometer.common.designsystem.resources.color.todometer_dark_surface

internal val colorPalette = Colors(
    primary = todometer_dark_primary,
    onPrimary = todometer_dark_onPrimary,
    secondary = todometer_dark_secondary,
    onSecondary = todometer_dark_onSecondary,
    error = todometer_dark_error,
    onError = todometer_dark_onError,
    surface = todometer_dark_surface,
    onSurface = todometer_dark_onSurface,
)
