package dev.sergiobelda.todometer.common.designsystem.resources.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import dev.sergiobelda.todometer.common.designsystem.resources.R

actual val TodometerIllustrations.NoTasks: Painter
    @Composable
    get() = painterResource(R.drawable.no_tasks)
