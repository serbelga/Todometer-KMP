package dev.sergiobelda.todometer.common.compose.ui.preferences

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.painterResource
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
internal fun AppTheme.themeIcon(): Painter =
    when (this) {
        AppTheme.FOLLOW_SYSTEM -> painterResource(ToDometerIcons.followSystemTheme)
        AppTheme.DARK_THEME -> painterResource(ToDometerIcons.darkTheme)
        AppTheme.LIGHT_THEME -> painterResource(ToDometerIcons.lightTheme)
    }

@Composable
internal fun AppTheme.themeName(): String =
    when (this) {
        AppTheme.FOLLOW_SYSTEM -> stringResource(MR.strings.follow_system)
        AppTheme.DARK_THEME -> stringResource(MR.strings.dark_theme)
        AppTheme.LIGHT_THEME -> stringResource(MR.strings.light_theme)
    }
