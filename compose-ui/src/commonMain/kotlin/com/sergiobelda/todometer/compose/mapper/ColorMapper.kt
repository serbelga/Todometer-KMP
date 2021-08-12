package com.sergiobelda.todometer.compose.mapper

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.compose.ui.theme.*

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
