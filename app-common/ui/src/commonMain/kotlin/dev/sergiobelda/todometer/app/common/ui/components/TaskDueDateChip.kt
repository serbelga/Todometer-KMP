/*
 * Copyright 2022 Sergio Belda
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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.app.common.designsystem.components.TodometerChip
import dev.sergiobelda.todometer.app.common.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.common.designsystem.resources.images.TodometerIcons
import dev.sergiobelda.todometer.common.ui.task.TaskDueDate
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TaskDueDateChip(dueDate: Long, modifier: Modifier = Modifier) {
    val currentInstant =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val expired =
        currentInstant > Instant.fromEpochMilliseconds(dueDate).toLocalDateTime(TimeZone.UTC)

    val dueDateChipTint = if (expired) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha()
    }
    val dueDateChipOutline = if (expired) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.outline
    }
    TodometerChip(
        border = BorderStroke(1.dp, dueDateChipOutline),
        modifier = modifier
    ) {
        Icon(
            TodometerIcons.Event,
            contentDescription = null,
            modifier = Modifier.size(16.dp).padding(end = 4.dp),
            tint = dueDateChipTint
        )
        Text(
            TaskDueDate.getDueDateFormatted(dueDate),
            style = MaterialTheme.typography.labelLarge,
            color = dueDateChipTint
        )
    }
}
