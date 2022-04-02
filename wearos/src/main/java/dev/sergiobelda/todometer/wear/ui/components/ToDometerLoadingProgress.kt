package dev.sergiobelda.todometer.wear.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator

@Composable
fun ToDometerLoadingProgress() {
    CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
}