package dev.sergiobelda.todometer.common.designsystem.resources.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import dev.sergiobelda.todometer.common.designsystem.resources.MR

actual val TodometerIllustrations.NoTasks: Painter
    @Composable
    get() = painterResource(MR.images.no_tasks)
