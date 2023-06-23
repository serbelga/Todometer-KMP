package dev.sergiobelda.todometer.desktop.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import dev.sergiobelda.todometer.common.ui.viewmodel.BaseViewModel

@Composable
internal fun BaseViewModel.clearDisposableEffect() {
    DisposableEffect(Unit) {
        onDispose {
            clear()
        }
    }
}
