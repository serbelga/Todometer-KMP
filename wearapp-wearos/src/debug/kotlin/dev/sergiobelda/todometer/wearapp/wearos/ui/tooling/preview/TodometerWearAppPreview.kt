package dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.resources.ProvideTodometerStrings
import dev.sergiobelda.todometer.wearapp.wearos.ui.theme.TodometerTheme

@Composable
internal fun TodometerWearAppPreview(
    content: @Composable () -> Unit = {},
) {
    ProvideTodometerStrings {
        TodometerTheme {
            content.invoke()
        }
    }
}
