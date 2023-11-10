package dev.sergiobelda.todometer.common.designsystem.resources.images.illustrations

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import dev.sergiobelda.todometer.common.designsystem.resources.R
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images

// Use vectorResource to parse drawable no_tasks which contains @android
// colors in v31 definition to show dynamic colors.
// TODO: Adapt ImageVectorGeneration task in buildlogic to parse attr
//  to MaterialTheme colors.
actual val Images.Illustrations.NoTasks: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.no_tasks)
