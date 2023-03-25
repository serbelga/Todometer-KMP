package dev.sergiobelda.todometer.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

expect class PainterResource

@Composable
expect fun painterResource(resource: PainterResource): Painter
