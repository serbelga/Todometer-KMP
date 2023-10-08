package dev.sergiobelda.todometer.common.resources

import androidx.compose.runtime.Composable
import cafe.adriel.lyricist.LocalStrings
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings

@Composable
fun rememberStrings(): Lyricist<TodometerStrings> =
    rememberStrings(Strings)

@Composable
fun ProvideTodometerStrings(
    lyricist: Lyricist<TodometerStrings> = rememberStrings(),
    content: @Composable () -> Unit
) {
    ProvideStrings(lyricist, LocalStrings, content)
}
