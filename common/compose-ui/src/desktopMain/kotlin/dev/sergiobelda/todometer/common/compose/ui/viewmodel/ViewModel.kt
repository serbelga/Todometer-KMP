package dev.sergiobelda.todometer.common.compose.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import dev.sergiobelda.todometer.common.ui.viewmodel.ViewModel

@Composable
fun ViewModel.subscribeToComposition() {
    DisposableEffect(Unit) {
        onDispose {
            clear()
        }
    }
}
