package dev.sergiobelda.todometer.common.compose.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalToDometerColors = staticCompositionLocalOf { toDometerLightColors() }

expect object ToDometerTheme {
    val toDometerColors: ToDometerColors
}

@Composable
fun ToDometerTheme(
    toDometerColors: ToDometerColors = ToDometerTheme.toDometerColors,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    shapes: Shapes = MaterialTheme.shapes,
    typography: Typography = MaterialTheme.typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalToDometerColors provides toDometerColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }
}
