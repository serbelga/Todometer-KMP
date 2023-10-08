/*
 * Copyright 2023 Sergio Belda
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

package dev.sergiobelda.todometer.app.common.ui.preferences

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.sergiobelda.todometer.common.designsystem.resources.images.TodometerIcons
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
fun AppTheme.themeIcon(): Painter =
    when (this) {
        AppTheme.FOLLOW_SYSTEM -> TodometerIcons.Contrast
        AppTheme.DARK_THEME -> TodometerIcons.DarkMode
        AppTheme.LIGHT_THEME -> TodometerIcons.LightMode
    }

@Composable
fun AppTheme.themeName(): String =
    when (this) {
        AppTheme.FOLLOW_SYSTEM -> TodometerResources.strings.follow_system
        AppTheme.DARK_THEME -> TodometerResources.strings.dark_theme
        AppTheme.LIGHT_THEME -> TodometerResources.strings.light_theme
    }
