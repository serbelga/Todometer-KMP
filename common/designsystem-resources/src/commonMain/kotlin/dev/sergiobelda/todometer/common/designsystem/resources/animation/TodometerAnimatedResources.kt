package dev.sergiobelda.todometer.common.designsystem.resources.animation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

expect object TodometerAnimatedResources {

    @Composable
    fun deleteAnimatedVectorPainter(
        atEnd: Boolean
    ): Painter
}
