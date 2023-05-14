package dev.sergiobelda.todometer.glance.theme

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.glance.GlanceComposable
import androidx.glance.GlanceTheme
import androidx.glance.material3.ColorProviders
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.DarkColorScheme
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.LightColorScheme

@Composable
fun ToDometerWidgetTheme(content: @GlanceComposable @Composable () -> Unit) {
    GlanceTheme(
        colors = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            GlanceTheme.colors
        } else {
            ColorProviders(
                light = LightColorScheme,
                dark = DarkColorScheme
            )
        },
        content = content
    )
}
