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

package dev.sergiobelda.todometer.common.compose.ui.taskdetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.AddChecklistItemField
import dev.sergiobelda.todometer.common.compose.ui.components.TaskDueDateChip
import dev.sergiobelda.todometer.common.compose.ui.components.TaskTagIndicator
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.TodometerCheckbox
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.TodometerDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.common.compose.ui.loading.LoadingScreenDialog
import dev.sergiobelda.todometer.common.compose.ui.values.SectionPadding
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItem
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.TodometerIcons
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    navigateToEditTask: () -> Unit,
    navigateBack: () -> Unit,
    taskDetailsUiState: TaskDetailsUiState,
    onTaskChecklistItemClick: (String, Boolean) -> Unit,
    onDeleteTaskCheckListItem: (String) -> Unit,
    onAddTaskCheckListItem: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    when {
        taskDetailsUiState.isLoadingTask -> {
            LoadingScreenDialog(navigateBack)
        }

        !taskDetailsUiState.isLoadingTask && taskDetailsUiState.task != null -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = {
                            if (lazyListState.firstVisibleItemIndex > 0) {
                                if (!taskDetailsUiState.isLoadingTask) {
                                    Text(
                                        taskDetailsUiState.task.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = navigateBack) {
                                Icon(
                                    TodometerIcons.NavigateBefore,
                                    contentDescription = stringResource(MR.strings.back)
                                )
                            }
                        },
                        actions = {
                            if (!taskDetailsUiState.isLoadingTask) {
                                IconButton(onClick = navigateToEditTask) {
                                    Icon(
                                        TodometerIcons.Edit,
                                        contentDescription = stringResource(MR.strings.edit_task),
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
                        TodometerDivider()
                    }
                    LazyColumn(state = lazyListState, modifier = Modifier.padding(paddingValues)) {
                        taskTitle(taskDetailsUiState.task)
                        taskChips(taskDetailsUiState.task)
                        taskChecklist(
                            taskDetailsUiState.taskChecklistItems,
                            onTaskChecklistItemClick = onTaskChecklistItemClick,
                            onDeleteTaskCheckListItem = onDeleteTaskCheckListItem,
                            onAddTaskCheckListItem = onAddTaskCheckListItem
                        )
                        taskDescription(taskDetailsUiState.task.description)
                    }
                }
            )
        }
    }
}

private fun LazyListScope.taskTitle(task: Task) {
    item {
        Surface(modifier = Modifier.heightIn(max = 80.dp, min = 64.dp)) {
            Row(
                modifier = Modifier.padding(start = 24.dp, bottom = 8.dp, end = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (task.tag != Tag.UNSPECIFIED) {
                    TaskTagIndicator(task.tag)
                }
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 4.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        TodometerDivider()
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
        TaskDetailSectionTitle(stringResource(MR.strings.checklist))
    }
    items(taskChecklistItems, key = { it.id }) { taskChecklistItem ->
        TaskChecklistItem(taskChecklistItem, onTaskChecklistItemClick, onDeleteTaskCheckListItem)
    }
    item {
        AddChecklistItemField(
            placeholder = { Text(stringResource(MR.strings.add_element)) },
            onAddTaskCheckListItem = onAddTaskCheckListItem
        )
    }
    item {
        TodometerDivider()
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun LazyItemScope.TaskChecklistItem(
    taskChecklistItem: TaskChecklistItem,
    onTaskChecklistItemClick: (String, Boolean) -> Unit,
    onDeleteTaskCheckListItem: (String) -> Unit
) {
    val textColor = if (taskChecklistItem.state == TaskChecklistItemState.CHECKED) {
        MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha()
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    val textDecoration = if (taskChecklistItem.state == TaskChecklistItemState.CHECKED) {
        TextDecoration.LineThrough
    } else {
        null
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onTaskChecklistItemClick(
                    taskChecklistItem.id,
                    taskChecklistItem.state == TaskChecklistItemState.UNCHECKED
                )
            }
            .animateItemPlacement()
            .padding(horizontal = 8.dp)
    ) {
        TodometerCheckbox(
            checked = taskChecklistItem.state == TaskChecklistItemState.CHECKED,
            onCheckedChange = { checked ->
                onTaskChecklistItemClick(
                    taskChecklistItem.id,
                    checked
                )
            },
            modifier = Modifier.scale(0.85f)
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
                TodometerIcons.Close,
                contentDescription = stringResource(MR.strings.clear),
                tint = MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha()
            )
        }
    }
}

private fun LazyListScope.taskDescription(description: String?) {
    item {
        Spacer(modifier = Modifier.height(24.dp))
    }
    item {
        TaskDetailSectionTitle(stringResource(MR.strings.description))
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
                text = stringResource(MR.strings.no_description),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = SectionPadding,
                    end = SectionPadding,
                    bottom = SectionPadding
                ),
                color = MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha()
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
