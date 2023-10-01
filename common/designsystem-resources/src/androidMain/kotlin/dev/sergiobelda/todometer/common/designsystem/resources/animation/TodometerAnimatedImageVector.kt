package dev.sergiobelda.todometer.common.designsystem.resources.animation

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.designsystem.resources.R

object TodometerAnimatedImageVector {

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    val Delete: AnimatedImageVector
        @Composable
        get() = AnimatedImageVector.animatedVectorResource(R.drawable.avd_delete)
}
