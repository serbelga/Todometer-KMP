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

package dev.sergiobelda.todometer.ui.taskdetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.compose.ui.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerCheckbox
import dev.sergiobelda.todometer.common.compose.ui.mapper.composeColorOf
import dev.sergiobelda.todometer.common.compose.ui.task.TaskDueDateChip
import dev.sergiobelda.todometer.common.compose.ui.taskchecklistitem.AddChecklistItemField
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerTypography
import dev.sergiobelda.todometer.common.compose.ui.theme.onSurfaceMediumEmphasis
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItem
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import dev.sergiobelda.todometer.ui.components.ToDometerContentLoadingProgress

private const val SECTION_PADDING = 32

@Composable
fun TaskDetailScreen(
    editTask: () -> Unit,
    navigateUp: () -> Unit,
    taskDetailViewModel: TaskDetailViewModel
) {
    val lazyListState = rememberLazyListState()
    val taskDetailUiState = taskDetailViewModel.taskDetailUiState
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (lazyListState.firstVisibleItemIndex > 0) {
                        if (!taskDetailUiState.isLoadingTask && taskDetailUiState.task != null) {
                            Text(taskDetailUiState.task.title)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = TodometerColors.onSurfaceMediumEmphasis
                        )
                    }
                },
                actions = {
                    if (!taskDetailUiState.isLoadingTask && taskDetailUiState.task != null) {
                        IconButton(onClick = editTask) {
                            Icon(
                                Icons.Outlined.Edit,
                                contentDescription = stringResource(R.string.edit_task),
                                tint = TodometerColors.primary
                            )
                        }
                    }
                },
                elevation = 0.dp,
                backgroundColor = TodometerColors.surface,
                contentColor = contentColorFor(backgroundColor = TodometerColors.surface)
            )
        },
        content = {
            if (lazyListState.firstVisibleItemIndex > 0) {
                HorizontalDivider()
            }
            if (taskDetailUiState.isLoadingTask) {
                ToDometerContentLoadingProgress()
            } else {
                taskDetailUiState.task?.let { task ->
                    LazyColumn(state = lazyListState) {
                        taskTitle(task)
                        taskChips(task)
                        taskChecklist(
                            taskDetailUiState.taskChecklistItems,
                            onTaskChecklistItemClick = { id, checked ->
                                if (checked) taskDetailViewModel.setTaskChecklistItemChecked(id) else taskDetailViewModel.setTaskChecklistItemUnchecked(
                                    id
                                )
                            },
                            onDeleteTaskCheckListItem = { id ->
                                taskDetailViewModel.deleteTaskChecklistItem(id)
                            },
                            onAddTaskCheckListItem = { text ->
                                taskDetailViewModel.insertTaskChecklistItem(text)
                            }
                        )
                        taskDescription(task.description)
                    }
                }
            }
        }
    )
}

private fun LazyListScope.taskTitle(task: Task) {
    item {
        Surface(modifier = Modifier.height(64.dp)) {
            Row(
                modifier = Modifier.padding(start = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(TodometerColors.composeColorOf(task.tag))
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                    Text(
                        text = task.title,
                        style = TodometerTypography.h6,
                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                        maxLines = 1
                    )
                }
            }
        }
        HorizontalDivider()
    }
}

private fun LazyListScope.taskChips(task: Task) {
    item {
        task.dueDate?.let {
            TaskDueDateChip(it, modifier = Modifier.padding(start = 24.dp, top = 24.dp))
        }
    }
}

private fun LazyListScope.taskChecklist(
    taskChecklistItems: List<TaskChecklistItem>,
    onTaskChecklistItemClick: (String, Boolean) -> Unit,
    onDeleteTaskCheckListItem: (String) -> Unit,
    onAddTaskCheckListItem: (String) -> Unit
) {
    item {
        Spacer(modifier = Modifier.height(24.dp))
    }
    item {
        TaskDetailSectionTitle(stringResource(R.string.checklist))
    }
    items(taskChecklistItems, key = { it.id }) { taskChecklistItem ->
        TaskChecklistItem(taskChecklistItem, onTaskChecklistItemClick, onDeleteTaskCheckListItem)
    }
    item {
        AddChecklistItemField(
            placeholder = { Text(stringResource(R.string.add_element)) },
            onAddTaskCheckListItem = onAddTaskCheckListItem
        )
    }
    item {
        HorizontalDivider()
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun LazyItemScope.TaskChecklistItem(
    taskChecklistItem: TaskChecklistItem,
    onTaskChecklistItemClick: (String, Boolean) -> Unit,
    onDeleteTaskCheckListItem: (String) -> Unit
) {
    val textColor =
        if (taskChecklistItem.state == TaskChecklistItemState.CHECKED) TodometerColors.onSurfaceMediumEmphasis else TodometerColors.onSurface
    val textDecoration =
        if (taskChecklistItem.state == TaskChecklistItemState.CHECKED) TextDecoration.LineThrough else null
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable {
            onTaskChecklistItemClick(
                taskChecklistItem.id,
                taskChecklistItem.state == TaskChecklistItemState.UNCHECKED
            )
        }.animateItemPlacement()
    ) {
        ToDometerCheckbox(
            checked = taskChecklistItem.state == TaskChecklistItemState.CHECKED,
            onCheckedChange = { checked ->
                onTaskChecklistItemClick(
                    taskChecklistItem.id,
                    checked
                )
            },
            modifier = Modifier.scale(0.85f).padding(start = 16.dp)
        )
        Text(
            text = taskChecklistItem.text,
            modifier = Modifier.weight(1f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = textColor,
            textDecoration = textDecoration
        )
        IconButton(onClick = { onDeleteTaskCheckListItem(taskChecklistItem.id) }) {
            Icon(
                Icons.Rounded.Clear,
                contentDescription = stringResource(R.string.clear),
                tint = TodometerColors.onSurfaceMediumEmphasis
            )
        }
    }
}

private fun LazyListScope.taskDescription(description: String?) {
    item {
        Spacer(modifier = Modifier.height(24.dp))
    }
    item {
        TaskDetailSectionTitle(stringResource(R.string.description))
    }
    item {
        if (!description.isNullOrBlank()) {
            Text(
                text = description,
                style = TodometerTypography.body1,
                modifier = Modifier.padding(
                    start = SECTION_PADDING.dp,
                    end = SECTION_PADDING.dp,
                    bottom = SECTION_PADDING.dp
                )
            )
        } else {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.no_description),
                    style = TodometerTypography.body1,
                    modifier = Modifier.padding(
                        top = 16.dp, start = SECTION_PADDING.dp,
                        end = SECTION_PADDING.dp,
                        bottom = SECTION_PADDING.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun TaskDetailSectionTitle(text: String) {
    Text(
        text,
        color = TodometerColors.primary,
        style = TodometerTypography.caption,
        modifier = Modifier.padding(start = SECTION_PADDING.dp, bottom = 8.dp)
    )
}
