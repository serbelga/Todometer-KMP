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

package dev.sergiobelda.todometer.common.compose.ui.tasklist

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
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.ui.task.TaskProgress

@Composable
fun TaskListProgress(taskListName: String?, tasks: List<TaskItem>, modifier: Modifier = Modifier) {
    val progress = TaskProgress.getTasksDoneProgress(tasks)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Column(
        modifier = modifier.fillMaxWidth().requiredWidthIn(max = 360.dp)
            .padding(start = 8.dp, end = 8.dp, bottom = 12.dp)
    ) {
        Text(
            taskListName?.uppercase() ?: "",
            style = TodometerTypography.overline,
            maxLines = 2
        )
        Text(
            text = TaskProgress.getPercentage(progress),
            style = TodometerTypography.body2,
            modifier = Modifier.padding(top = 4.dp)
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier.fillMaxWidth().paddingFromBaseline(8.dp)
                .clip(RoundedCornerShape(2.dp))
        )
    }
}
