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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.compose.ui.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerCheckbox
import dev.sergiobelda.todometer.common.compose.ui.mapper.composeColorOf
import dev.sergiobelda.todometer.common.compose.ui.task.TaskDueDateChip
import dev.sergiobelda.todometer.common.compose.ui.taskchecklistitem.AddChecklistItemField
import dev.sergiobelda.todometer.common.compose.ui.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItem
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import dev.sergiobelda.todometer.ui.components.ToDometerContentLoadingProgress

private val SectionPadding: Dp = 32.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskDetailScreen(
    editTask: () -> Unit,
    navigateUp: () -> Unit,
    taskDetailViewModel: TaskDetailViewModel
) {
    val lazyListState = rememberLazyListState()
    val taskDetailUiState = taskDetailViewModel.taskDetailUiState
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = {
                    if (lazyListState.firstVisibleItemIndex > 0) {
                        if (!taskDetailUiState.isLoadingTask && taskDetailUiState.task != null) {
                            Text(
                                taskDetailUiState.task.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
                        )
                    }
                },
                actions = {
                    if (!taskDetailUiState.isLoadingTask && taskDetailUiState.task != null) {
                        IconButton(onClick = editTask) {
                            Icon(
                                Icons.Outlined.Edit,
                                contentDescription = stringResource(R.string.edit_task),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { paddingValues ->
            if (lazyListState.firstVisibleItemIndex > 0) {
                HorizontalDivider()
            }
            if (taskDetailUiState.isLoadingTask) {
                ToDometerContentLoadingProgress()
            } else {
                taskDetailUiState.task?.let { task ->
                    LazyColumn(state = lazyListState, modifier = Modifier.padding(paddingValues)) {
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
        Surface(modifier = Modifier.heightIn(max = 80.dp, min = 64.dp)) {
            Row(
                modifier = Modifier.padding(start = 24.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(ToDometerTheme.toDometerColors.composeColorOf(task.tag))
                )
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp, end = 8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
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
        if (taskChecklistItem.state == TaskChecklistItemState.CHECKED) ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis else MaterialTheme.colorScheme.onSurface
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
                tint = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
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
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    start = SectionPadding,
                    end = SectionPadding,
                    bottom = SectionPadding
                )
            )
        } else {
            Text(
                text = stringResource(id = R.string.no_description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = SectionPadding,
                    end = SectionPadding,
                    bottom = SectionPadding
                ),
                color = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
            )
        }
    }
}

@Composable
private fun TaskDetailSectionTitle(text: String) {
    Text(
        text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(start = SectionPadding, bottom = 8.dp)
    )
}
