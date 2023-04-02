package dev.sergiobelda.todometer.common.compose.ui.components.task

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sergiobelda.todometer.common.compose.ui.R
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalAnimationGraphicsApi::class, ExperimentalMaterialApi::class)
@Composable
internal actual fun TaskItemBackgroundIcon(dismissState: DismissState, backgroundIconTint: Color) {
    val icon = AnimatedImageVector.animatedVectorResource(R.drawable.avd_delete)
    Icon(
        painter = rememberAnimatedVectorPainter(
            icon,
            atEnd = dismissState.targetValue == DismissValue.DismissedToEnd
        ),
        contentDescription = stringResource(MR.strings.delete_task),
        tint = backgroundIconTint
    )
}
