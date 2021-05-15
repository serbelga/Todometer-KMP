package com.sergiobelda.todometer.compose.mapper

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import com.sergiobelda.todometer.common.model.Color
import com.sergiobelda.todometer.compose.ui.theme.blue
import com.sergiobelda.todometer.compose.ui.theme.indigo
import com.sergiobelda.todometer.compose.ui.theme.pink
import com.sergiobelda.todometer.compose.ui.theme.red
import com.sergiobelda.todometer.compose.ui.theme.teal

typealias ComposeColor = androidx.compose.ui.graphics.Color

@Composable
fun Colors.composeColorOf(color: Color): ComposeColor {
    return when (color) {
        Color.BLUE -> blue
        Color.INDIGO -> indigo
        Color.PINK -> pink
        Color.RED -> red
        Color.TEAL -> teal
        else -> red
    }
}
