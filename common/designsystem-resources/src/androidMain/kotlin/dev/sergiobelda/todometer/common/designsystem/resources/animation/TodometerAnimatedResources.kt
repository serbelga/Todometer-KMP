package dev.sergiobelda.todometer.common.designsystem.resources.animation

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.sergiobelda.todometer.common.designsystem.resources.R

actual object TodometerAnimatedResources {

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    @Composable
    actual fun deleteAnimatedVectorPainter(
        atEnd: Boolean
    ): Painter =
        rememberAnimatedVectorPainter(
            animatedImageVector = AnimatedImageVector.animatedVectorResource(R.drawable.avd_delete),
            atEnd = atEnd
        )
}
