package com.sergiobelda.todometer.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleLineItem(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit),
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier.heightIn(min = 56.dp).fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Box(modifier = Modifier.padding(start = 24.dp)) {
                it()
            }
        }
        Box(modifier = Modifier.padding(start = 24.dp)) {
            text()
        }
    }
}
