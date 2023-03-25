package dev.sergiobelda.todometer.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter

@Immutable
actual class PainterResource(val resourcePath: String)

@Composable
actual fun painterResource(resource: PainterResource): Painter =
    BitmapPainter(image = ImageBitmap(width = 24, height = 24))
