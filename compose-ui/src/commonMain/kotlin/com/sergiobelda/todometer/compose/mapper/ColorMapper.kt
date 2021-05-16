package com.sergiobelda.todometer.compose.mapper

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import com.sergiobelda.todometer.common.model.Color
import com.sergiobelda.todometer.compose.ui.theme.amber
import com.sergiobelda.todometer.compose.ui.theme.blue
import com.sergiobelda.todometer.compose.ui.theme.brown
import com.sergiobelda.todometer.compose.ui.theme.gray
import com.sergiobelda.todometer.compose.ui.theme.green
import com.sergiobelda.todometer.compose.ui.theme.indigo
import com.sergiobelda.todometer.compose.ui.theme.lime
import com.sergiobelda.todometer.compose.ui.theme.orange
import com.sergiobelda.todometer.compose.ui.theme.pink
import com.sergiobelda.todometer.compose.ui.theme.red
import com.sergiobelda.todometer.compose.ui.theme.teal
import com.sergiobelda.todometer.compose.ui.theme.yellow

typealias ComposeColor = androidx.compose.ui.graphics.Color

@Composable
fun Colors.composeColorOf(color: Color): ComposeColor {
    return when (color) {
        Color.PINK -> pink
        Color.RED -> red
        Color.INDIGO -> indigo
        Color.BLUE -> blue
        Color.TEAL -> teal
        Color.GREEN -> green
        Color.LIME -> lime
        Color.YELLOW -> yellow
        Color.AMBER -> amber
        Color.ORANGE -> orange
        Color.BROWN -> brown
        Color.GRAY -> gray
        else -> gray
    }
}
