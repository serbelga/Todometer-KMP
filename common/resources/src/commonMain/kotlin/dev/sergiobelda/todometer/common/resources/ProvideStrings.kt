package dev.sergiobelda.todometer.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings

val LocalTodometerStrings = compositionLocalOf { EnTodometerStrings }

@Composable
fun rememberStrings(): Lyricist<TodometerStrings> =
    rememberStrings(Strings)

@Composable
fun ProvideTodometerStrings(
    lyricist: Lyricist<TodometerStrings> = rememberStrings(),
    content: @Composable () -> Unit,
) {
    ProvideStrings(lyricist, LocalTodometerStrings, content)
}

object TodometerResources {
    val strings: TodometerStrings
        @Composable
        @ReadOnlyComposable
        get() = LocalTodometerStrings.current
}
