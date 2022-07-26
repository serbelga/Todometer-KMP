package dev.sergiobelda.todometer.common.compose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

actual object ToDometerTheme {
    actual val toDometerColors: ToDometerColors
        @Composable
        @ReadOnlyComposable
        get() = LocalToDometerColors.current
}
