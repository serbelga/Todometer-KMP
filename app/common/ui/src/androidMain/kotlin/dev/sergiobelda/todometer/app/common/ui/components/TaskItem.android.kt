/*
 * Copyright 2023 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.todometer.app.common.ui.components

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.sergiobelda.todometer.common.designsystem.resources.animation.TodometerAnimatedImageVector
import dev.sergiobelda.todometer.common.resources.TodometerResources

@OptIn(ExperimentalAnimationGraphicsApi::class, ExperimentalMaterial3Api::class)
@Composable
internal actual fun TaskItemBackgroundIcon(
    state: SwipeToDismissBoxState,
    backgroundIconTint: Color
) {
    Icon(
        painter = rememberAnimatedVectorPainter(
            TodometerAnimatedImageVector.Delete,
            atEnd = state.targetValue == SwipeToDismissBoxValue.StartToEnd
        ),
        contentDescription = TodometerResources.strings.delete_task,
        tint = backgroundIconTint
    )
}
