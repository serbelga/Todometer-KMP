package com.sergiobelda.todometer.compose.mapper

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import com.sergiobelda.todometer.common.model.Tag
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
fun Colors.composeColorOf(tag: Tag): ComposeColor {
    return when (tag) {
        Tag.PINK -> pink
        Tag.RED -> red
        Tag.INDIGO -> indigo
        Tag.BLUE -> blue
        Tag.TEAL -> teal
        Tag.GREEN -> green
        Tag.LIME -> lime
        Tag.YELLOW -> yellow
        Tag.AMBER -> amber
        Tag.ORANGE -> orange
        Tag.BROWN -> brown
        Tag.GRAY -> gray
        else -> gray
    }
}
