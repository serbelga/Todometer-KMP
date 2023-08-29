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

package dev.sergiobelda.todometer.common.compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.common.compose.ui.values.SectionPadding
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.stringResource
import dev.sergiobelda.todometer.common.ui.task.TaskDueDate

@Composable
internal fun DateTimeSelector(
    taskDueDate: Long?,
    onClick: () -> Unit,
    onClearDateTimeClick: () -> Unit
) {
    Column {
        Text(
            text = stringResource(MR.strings.date_time),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = SectionPadding)
        )
        Row(
            modifier = Modifier.height(48.dp).fillMaxWidth().clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = taskDueDate?.let { TaskDueDate.getDueDateFormatted(it) }
                    ?: run { stringResource(MR.strings.enter_date_time) },
                color = MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha(),
                modifier = Modifier.padding(horizontal = SectionPadding)
            )
            taskDueDate?.let {
                IconButton(onClick = onClearDateTimeClick) {
                    Icon(
                        ToDometerIcons.Close,
                        stringResource(MR.strings.clear),
                        tint = MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha()
                    )
                }
            }
        }
    }
}
