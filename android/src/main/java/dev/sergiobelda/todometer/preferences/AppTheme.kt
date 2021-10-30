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

package dev.sergiobelda.todometer.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.preferences.AppTheme

data class AppThemeOption(
    @DrawableRes val themeIconRes: Int,
    @StringRes val modeNameRes: Int
)

val appThemeMap = mapOf(
    AppTheme.FOLLOW_SYSTEM to AppThemeOption(
        R.drawable.ic_baseline_default_theme_24,
        R.string.follow_system
    ),
    AppTheme.LIGHT_THEME to AppThemeOption(
        R.drawable.ic_baseline_light_theme_24,
        R.string.light_theme
    ),
    AppTheme.DARK_THEME to AppThemeOption(
        R.drawable.ic_baseline_dark_theme_24,
        R.string.dark_theme
    )
)
