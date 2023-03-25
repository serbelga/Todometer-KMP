package dev.sergiobelda.todometer.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Immutable
actual class PainterResource(val resourcePath: String)

@Composable
actual fun painterResource(resource: PainterResource): Painter =
    painterResource(resourcePath = resource.resourcePath)
