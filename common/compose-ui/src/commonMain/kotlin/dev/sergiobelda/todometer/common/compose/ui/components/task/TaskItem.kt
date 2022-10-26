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

package dev.sergiobelda.todometer.common.compose.ui.components.task

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.compose.ui.mapper.composeColorOf
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    taskItem: TaskItem,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onClick: (String) -> Unit,
    onLongClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.combinedClickable(
            onClick = {
                onClick(taskItem.id)
            },
            onLongClick = {
                onLongClick(taskItem.id)
            }
        ).fillMaxWidth().background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 20.dp, end = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(ToDometerTheme.toDometerColors.composeColorOf(taskItem.tag))
            )
            when (taskItem.state) {
                TaskState.DOING -> {
                    Text(
                        taskItem.title,
                        modifier = Modifier.padding(start = 8.dp).weight(1f),
                        maxLines = 1
                    )
                    IconButton(
                        onClick = { onDoneClick(taskItem.id) }
                    ) {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = "Done",
                            tint = ToDometerTheme.toDometerColors.check
                        )
                    }
                }
                TaskState.DONE -> {
                    Text(
                        taskItem.title,
                        textDecoration = TextDecoration.LineThrough,
                        color = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis,
                        modifier = Modifier.padding(start = 8.dp).weight(1f),
                        maxLines = 1
                    )
                    IconButton(
                        onClick = { onDoingClick(taskItem.id) }
                    ) {
                        Icon(
                            Icons.Filled.Replay,
                            contentDescription = "Doing",
                            tint = ToDometerTheme.toDometerColors.check
                        )
                    }
                }
            }
        }
        TaskItemAdditionalInformationRow(taskItem)
        HorizontalDivider()
    }
}

@Composable
internal fun TaskItemAdditionalInformationRow(taskItem: TaskItem) {
    LazyRow(
        modifier = Modifier.padding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.width(32.dp))
        }
        if (taskItem.state == TaskState.DOING) {
            taskItem.dueDate?.let { dueDate ->
                item {
                    TaskDueDateChip(dueDate, modifier = Modifier.padding(bottom = 8.dp))
                }
            }
        }
        if (taskItem.totalChecklistItems > 0) {
            item {
                TaskChecklistItemsChip(taskItem.checklistItemsDone, taskItem.totalChecklistItems)
            }
        }
        item {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
