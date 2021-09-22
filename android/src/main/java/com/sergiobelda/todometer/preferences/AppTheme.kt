package com.sergiobelda.todometer.preferences

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.preferences.AppTheme

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
