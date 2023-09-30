package dev.sergiobelda.todometer.common.designsystem.resources.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource

@Composable
internal fun painterResource(resource: ImageResource): Painter =
    painterResource(resource)
