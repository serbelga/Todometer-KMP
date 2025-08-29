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

package dev.sergiobelda.todometer.app.common.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.app.common.designsystem.theme.Alpha
import dev.sergiobelda.todometer.app.common.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.app.common.ui.animation.LocalAnimatedContentScope
import dev.sergiobelda.todometer.app.common.ui.animation.LocalSharedTransitionScope
import dev.sergiobelda.todometer.app.common.ui.animation.TaskSharedElementKey
import dev.sergiobelda.todometer.app.common.ui.animation.TaskSharedElementType
import dev.sergiobelda.todometer.common.designsystem.resources.animation.TodometerAnimatedResources
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.CheckCircle
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.RadioButtonUnchecked
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.TaskAlt
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
fun TaskItem(
    taskItem: TaskItem,
    onDoingClick: () -> Unit,
    onDoneClick: () -> Unit,
    onTaskItemClick: () -> Unit,
    onTaskItemLongClick: () -> Unit,
    onSwipeToDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    swipeable: Boolean = false,
    checkEnabled: Boolean = false,
    selected: Boolean = false,
) {
    if (!swipeable) {
        TaskItemContent(
            taskItem,
            onDoingClick = onDoingClick,
            onDoneClick = onDoneClick,
            onClick = onTaskItemClick,
            onLongClick = onTaskItemLongClick,
            checkEnabled = checkEnabled,
            selected = selected,
        )
    } else {
        val state = rememberSwipeToDismissBoxState(
            confirmValueChange = {
                if (it == SwipeToDismissBoxValue.StartToEnd) {
                    onSwipeToDismiss()
                }
                it != SwipeToDismissBoxValue.StartToEnd
            },
        )
        val taskItemShadowElevation by animateDpAsState(
            if (state.targetValue != SwipeToDismissBoxValue.Settled) TaskItemDismissedShadowElevation else 0.dp,
            animationSpec = tween(
                durationMillis = TaskItemShadowElevationAnimationDuration,
                easing = FastOutSlowInEasing,
            ),
        )
        SwipeToDismissBox(
            state = state,
            enableDismissFromEndToStart = false,
            backgroundContent = {
                TaskItemSwipeableBackgroundContent(
                    state = state,
                )
            },
            content = {
                TaskItemContent(
                    taskItem,
                    onDoingClick = onDoingClick,
                    onDoneClick = onDoneClick,
                    onClick = onTaskItemClick,
                    onLongClick = onTaskItemLongClick,
                    shadowElevation = taskItemShadowElevation,
                    selected = selected,
                    checkEnabled = checkEnabled,
                )
            },
            modifier = modifier,
        )
    }
}

@Composable
private fun TaskItemSwipeableBackgroundContent(
    state: SwipeToDismissBoxState,
) {
    Box(
        Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clip(TaskItemBackgroundShape)
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(horizontal = TaskItemBackgroundHorizontalPadding),
        contentAlignment = Alignment.CenterStart,
    ) {
        Icon(
            painter = TodometerAnimatedResources.deleteAnimatedVectorPainter(
                atEnd = state.targetValue == SwipeToDismissBoxValue.StartToEnd,
            ),
            contentDescription = TodometerResources.strings.deleteTask,
            tint = MaterialTheme.colorScheme.onErrorContainer,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TaskItemContent(
    taskItem: TaskItem,
    onDoingClick: () -> Unit,
    onDoneClick: () -> Unit,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    checkEnabled: Boolean,
    selected: Boolean,
    modifier: Modifier = Modifier,
    shadowElevation: Dp = 0.dp,
) {
    val border = if (selected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null

    Box {
        Surface(
            shape = TaskItemShape,
            modifier = modifier.padding(TaskItemPadding),
            tonalElevation = TaskItemTonalElevation,
            shadowElevation = shadowElevation,
            border = border,
        ) {
            Column(
                modifier = Modifier.combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick,
                ),
            ) {
                TaskItemHeadlineContent(
                    taskItem = taskItem,
                    onDoingClick = onDoingClick,
                    onDoneClick = onDoneClick,
                    checkEnabled = checkEnabled,
                )
                TaskItemSupportingContent(taskItem)
            }
        }
        AnimatedVisibility(
            visible = selected,
            enter = scaleIn(
                animationSpec = tween(
                    durationMillis = TaskItemSelectedIconAnimationDuration,
                    easing = FastOutSlowInEasing,
                ),
            ),
            exit = fadeOut(),
        ) {
            Icon(
                Images.Icons.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = TaskItemSelectedIconPadding)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .size(TaskItemSelectedIconSize)
                    .align(Alignment.TopStart),
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TaskItemHeadlineContent(
    taskItem: TaskItem,
    onDoingClick: () -> Unit,
    onDoneClick: () -> Unit,
    checkEnabled: Boolean = true,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            start = TaskItemInnerPaddingStart,
            end = TaskItemInnerPaddingEnd,
        ),
    ) {
        with(sharedTransitionScope) {
            if (taskItem.tag != Tag.UNSPECIFIED) {
                TaskTagIndicator(
                    taskItem.tag,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(
                                key = TaskSharedElementKey(
                                    type = TaskSharedElementType.Tag,
                                    taskId = taskItem.id,
                                ),
                            ),
                            animatedVisibilityScope = animatedContentScope,
                        ),
                )
            }
            Text(
                taskItem.title,
                textDecoration = taskItemTitleTextDecoration(taskItem.state),
                color = taskItemTitleColor(taskItem.state),
                modifier = Modifier
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(
                            key = TaskSharedElementKey(
                                type = TaskSharedElementType.Title,
                                taskId = taskItem.id,
                            ),
                        ),
                        animatedVisibilityScope = animatedContentScope,
                    )
                    .weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        IconButton(
            onClick = {
                if (taskItem.state == TaskState.DONE) {
                    onDoingClick()
                } else {
                    onDoneClick()
                }
            },
            modifier = Modifier.alpha(if (checkEnabled) Alpha.Disabled else Alpha.High),
            enabled = !checkEnabled,
        ) {
            Icon(
                taskItemActionIcon(taskItem.state),
                contentDescription = taskItemActionContentDescription(taskItem.state),
                tint = taskItemActionTintColor(taskItem.state),
            )
        }
    }
}

@Composable
private fun TaskItemSupportingContent(taskItem: TaskItem) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.padding(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                TaskChecklistItemsChip(
                    taskItem.checklistItemsDone,
                    taskItem.totalChecklistItems,
                )
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
        TaskState.DOING -> MaterialTheme.colorScheme.onSurfaceVariant
        TaskState.DONE -> MaterialTheme.colorScheme.primary
    }

private fun taskItemActionIcon(state: TaskState): ImageVector =
    when (state) {
        TaskState.DOING -> Images.Icons.RadioButtonUnchecked
        TaskState.DONE -> Images.Icons.TaskAlt
    }

@Composable
private fun taskItemActionContentDescription(state: TaskState): String =
    when (state) {
        TaskState.DOING -> TodometerResources.strings.checkTask
        TaskState.DONE -> TodometerResources.strings.uncheckTask
    }

private val TaskItemBackgroundHorizontalPadding = 16.dp
private val TaskItemBackgroundShape: Shape = RoundedCornerShape(14.dp)

private val TaskItemInnerPaddingEnd: Dp = 8.dp
private val TaskItemInnerPaddingStart: Dp = 16.dp
private const val TaskItemSelectedIconAnimationDuration: Int = 150
private val TaskItemSelectedIconPadding: Dp = 2.dp
private val TaskItemSelectedIconSize: Dp = 20.dp
private val TaskItemPadding: Dp = 4.dp
private val TaskItemShape: Shape = RoundedCornerShape(12.dp)
private val TaskItemTonalElevation: Dp = 2.dp
private const val TaskItemShadowElevationAnimationDuration: Int = 400
private val TaskItemDismissedShadowElevation: Dp = 4.dp
