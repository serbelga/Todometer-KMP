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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun TaskItem(
    taskItem: TaskItem,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onTaskItemClick: (String) -> Unit,
    onTaskItemLongClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    swipeable: Boolean = false,
    checkEnabled: Boolean = false,
    selected: Boolean = false,
    onSwipeToDismiss: () -> Unit
) {
    if (!swipeable) {
        TaskItemContent(
            taskItem,
            onDoingClick = onDoingClick,
            onDoneClick = onDoneClick,
            onClick = onTaskItemClick,
            onLongClick = onTaskItemLongClick,
            checkEnabled = checkEnabled,
            selected = selected
        )
    } else {
        val dismissState = rememberDismissState(
            confirmStateChange = {
                if (it == DismissValue.DismissedToEnd) {
                    onSwipeToDismiss()
                }
                it != DismissValue.DismissedToEnd
            }
        )
        val taskItemShadowElevation by animateDpAsState(
            if (dismissState.targetValue != DismissValue.Default) TaskItemDismissedShadowElevation else 0.dp,
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing
            )
        )
        SwipeToDismiss(
            state = dismissState,
            directions = setOf(DismissDirection.StartToEnd),
            dismissThresholds = {
                FractionalThreshold(0.1f)
            },
            background = {
                Box(
                    Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .clip(TaskItemBackgroundShape)
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(horizontal = TaskItemBackgroundHorizontalPadding),
                    contentAlignment = Alignment.CenterStart
                ) {
                    TaskItemBackgroundIcon(
                        dismissState,
                        MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            },
            dismissContent = {
                TaskItemContent(
                    taskItem,
                    onDoingClick = onDoingClick,
                    onDoneClick = onDoneClick,
                    onClick = onTaskItemClick,
                    onLongClick = onTaskItemLongClick,
                    shadowElevation = taskItemShadowElevation,
                    selected = selected,
                    checkEnabled = checkEnabled
                )
            },
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal expect fun TaskItemBackgroundIcon(dismissState: DismissState, backgroundIconTint: Color)

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
private fun TaskItemContent(
    taskItem: TaskItem,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    onClick: (String) -> Unit,
    onLongClick: (String) -> Unit,
    checkEnabled: Boolean,
    selected: Boolean,
    modifier: Modifier = Modifier,
    shadowElevation: Dp = 0.dp
) {
    val border = if (selected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null

    Box {
        Surface(
            shape = TaskItemShape,
            modifier = modifier.padding(TaskItemPadding),
            tonalElevation = TaskItemTonalElevation,
            shadowElevation = shadowElevation,
            border = border
        ) {
            Column(
                modifier = Modifier.combinedClickable(
                    onClick = {
                        onClick(taskItem.id)
                    },
                    onLongClick = {
                        onLongClick(taskItem.id)
                    }
                )
            ) {
                TaskItemHeadlineContent(
                    taskItem = taskItem,
                    onDoingClick = onDoingClick,
                    onDoneClick = onDoneClick,
                    checkEnabled = checkEnabled
                )
                TaskItemSupportingContent(taskItem)
            }
        }
        AnimatedVisibility(
            visible = selected,
            enter = scaleIn(
                // TODO: Use const
                animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing)
            ),
            exit = fadeOut()
        ) {
            Icon(
                ToDometerIcons.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    // TODO: Improve
                    .padding(start = 2.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .size(20.dp)
                    .align(Alignment.TopStart),
            )
        }
    }


}

@Composable
private fun TaskItemHeadlineContent(
    taskItem: TaskItem,
    onDoingClick: (String) -> Unit,
    onDoneClick: (String) -> Unit,
    checkEnabled: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = TaskItemInnerPaddingStart,
            end = TaskItemInnerPaddingEnd
        )
    ) {
        Text(
            taskItem.title,
            textDecoration = taskItemTitleTextDecoration(taskItem.state),
            color = taskItemTitleColor(taskItem.state),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            onClick = {
                if (taskItem.state == TaskState.DONE) {
                    onDoingClick(taskItem.id)
                } else {
                    onDoneClick(taskItem.id)
                }
            },
            modifier = Modifier.alpha(if (checkEnabled) Alpha.Disabled else Alpha.High),
            enabled = !checkEnabled
        ) {
            Icon(
                taskItemActionIcon(taskItem.state),
                contentDescription = taskItemActionContentDescription(taskItem.state),
                tint = taskItemActionTintColor(taskItem.state)
            )
        }
    }
}

@Composable
private fun TaskItemSupportingContent(taskItem: TaskItem) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.padding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
    }
}

@Composable
private fun taskItemTitleColor(state: TaskState): Color =
    when (state) {
        TaskState.DOING -> MaterialTheme.colorScheme.onSurface
        TaskState.DONE -> MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha()
    }

private fun taskItemTitleTextDecoration(state: TaskState): TextDecoration =
    when (state) {
        TaskState.DOING -> TextDecoration.None
        TaskState.DONE -> TextDecoration.LineThrough
    }

@Composable
private fun taskItemActionTintColor(state: TaskState): Color =
    when (state) {
        TaskState.DOING -> MaterialTheme.colorScheme.onPrimaryContainer
        TaskState.DONE -> MaterialTheme.colorScheme.primary
    }

@Composable
private fun taskItemActionIcon(state: TaskState): Painter =
    when (state) {
        TaskState.DOING -> ToDometerIcons.RadioButtonUnchecked
        TaskState.DONE -> ToDometerIcons.TaskAlt
    }

@Composable
private fun taskItemActionContentDescription(state: TaskState): String =
    when (state) {
        TaskState.DOING -> stringResource(MR.strings.check_task)
        TaskState.DONE -> stringResource(MR.strings.uncheck_task)
    }

private val TaskItemBackgroundHorizontalPadding = 16.dp
private val TaskItemBackgroundShape: Shape = RoundedCornerShape(14.dp)

private val TaskItemInnerPaddingEnd: Dp = 8.dp
private val TaskItemInnerPaddingStart: Dp = 16.dp
private val TaskItemPadding: Dp = 4.dp
private val TaskItemShape: Shape = RoundedCornerShape(12.dp)
private val TaskItemTonalElevation: Dp = 2.dp
private val TaskItemDismissedShadowElevation: Dp = 4.dp
