/*
 * Copyright 2021 Sergio Belda
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

package dev.sergiobelda.todometer.common.compose.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerTypography
import dev.sergiobelda.todometer.common.compose.ui.util.ProgressUtil
import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.model.Task

@Composable
fun ProjectProgress(project: Project?, tasks: List<Task>) {
    val progress = ProgressUtil.getTasksDoneProgress(tasks)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Column(
        modifier = Modifier.fillMaxWidth().requiredWidthIn(max = 360.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        // TODO: 02/04/2021 Max lines
        Text(
            project?.name?.uppercase() ?: "-",
            style = TodometerTypography.overline
        )
        Text(
            text = ProgressUtil.getPercentage(progress),
            style = TodometerTypography.body2
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.fillMaxWidth().paddingFromBaseline(8.dp)
                .clip(RoundedCornerShape(2.dp))
        )
    }
}
