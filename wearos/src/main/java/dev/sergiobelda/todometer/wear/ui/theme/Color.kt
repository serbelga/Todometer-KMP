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

package dev.sergiobelda.todometer.wear.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

internal val todometer_dark_primary = Color(0xFFADC6FF)
internal val todometer_dark_onPrimary = Color(0xFF002E69)
internal val todometer_dark_secondary = Color(0xFF43E188)
internal val todometer_dark_onSecondary = Color(0xFF00391C)
internal val todometer_dark_tertiary = Color(0xFFFFB59D)
internal val todometer_dark_onTertiary = Color(0xFF5D1800)
internal val todometer_dark_error = Color(0xFFFFB4AB)
internal val todometer_dark_onError = Color(0xFF690005)
internal val todometer_dark_surface = Color(0xFF171B23)
internal val todometer_dark_onSurface = Color(0xFFE3E2E6)

internal val colorPalette = Colors(
    primary = todometer_dark_primary,
    onPrimary = todometer_dark_onPrimary,
    secondary = todometer_dark_secondary,
    onSecondary = todometer_dark_onSecondary,
    error = todometer_dark_error,
    onError = todometer_dark_onError,
    surface = todometer_dark_surface,
    onSurface = todometer_dark_onSurface
)
