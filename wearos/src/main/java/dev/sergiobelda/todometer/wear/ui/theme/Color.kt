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

val md_theme_dark_primary = Color(0xFFADC6FF)
val md_theme_dark_onPrimary = Color(0xFF002E69)
val md_theme_dark_secondary = Color(0xFF43E188)
val md_theme_dark_onSecondary = Color(0xFF00391C)
val md_theme_dark_tertiary = Color(0xFFFFB59D)
val md_theme_dark_onTertiary = Color(0xFF5D1800)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_surface = Color(0xFF171B23)
val md_theme_dark_onSurface = Color(0xFFE3E2E6)

val colorPalette = Colors(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface
)
