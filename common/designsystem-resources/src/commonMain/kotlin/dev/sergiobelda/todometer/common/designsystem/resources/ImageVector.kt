package dev.sergiobelda.todometer.common.designsystem.resources

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

inline fun imageVector(
    name: String,
    width: Float,
    height: Float,
    viewportWidth: Float,
    viewportHeight: Float,
    block: ImageVector.Builder.() -> ImageVector.Builder
): ImageVector = ImageVector.Builder(
    name = name,
    defaultWidth = width.dp,
    defaultHeight = height.dp,
    viewportWidth = viewportWidth,
    viewportHeight = viewportHeight
).block().build()
