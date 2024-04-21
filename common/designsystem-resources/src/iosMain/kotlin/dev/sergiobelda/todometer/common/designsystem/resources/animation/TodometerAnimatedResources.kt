package dev.sergiobelda.todometer.common.designsystem.resources.animation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Delete

actual object TodometerAnimatedResources {
    @Composable
    actual fun deleteAnimatedVectorPainter(atEnd: Boolean): Painter =
        rememberVectorPainter(
            Images.Icons.Delete
        )
}
