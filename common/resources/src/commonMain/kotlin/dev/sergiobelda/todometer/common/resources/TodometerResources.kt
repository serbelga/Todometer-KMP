package dev.sergiobelda.todometer.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import cafe.adriel.lyricist.LocalStrings

object TodometerResources {
    val strings: TodometerStrings
        @Composable
        @ReadOnlyComposable
        get() = LocalStrings.current
}
