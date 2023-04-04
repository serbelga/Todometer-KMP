package dev.sergiobelda.todometer.common.compose.ui.components.task

import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.painterResource
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal actual fun TaskItemBackgroundIcon(dismissState: DismissState, backgroundIconTint: Color) {
    Icon(
        painter = painterResource(ToDometerIcons.Delete),
        contentDescription = stringResource(MR.strings.delete_task),
        tint = backgroundIconTint
    )
}
