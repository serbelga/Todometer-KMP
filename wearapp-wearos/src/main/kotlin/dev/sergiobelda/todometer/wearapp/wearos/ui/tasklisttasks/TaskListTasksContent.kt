/*
 * Copyright 2025 Sergio Belda
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

package dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks

import android.app.Activity
import android.app.RemoteInput
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListScope
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.requestFocusOnHierarchyActive
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.ProgressIndicatorDefaults
import androidx.wear.compose.material.RevealState
import androidx.wear.compose.material.RevealValue.Companion.Covered
import androidx.wear.compose.material.RevealValue.Companion.LeftRevealing
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.SplitToggleChip
import androidx.wear.compose.material.SwipeToRevealChip
import androidx.wear.compose.material.SwipeToRevealDefaults.createRevealAnchors
import androidx.wear.compose.material.SwipeToRevealPrimaryAction
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChipDefaults
import androidx.wear.compose.material.curvedText
import androidx.wear.compose.material.rememberRevealState
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import dev.sergiobelda.fonament.presentation.ui.FonamentContent
import dev.sergiobelda.todometer.common.designsystem.resources.animation.TodometerAnimatedResources
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Add
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Delete
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Edit
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.RadioButtonUnchecked
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.TaskAlt
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.resources.TodometerResources
import dev.sergiobelda.todometer.common.ui.task.TaskProgress
import dev.sergiobelda.todometer.wearapp.wearos.ui.deletetask.DeleteTaskAlertDialog
import dev.sergiobelda.todometer.wearapp.wearos.ui.deletetasklist.DeleteTaskListAlertDialog
import dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks.navigation.TaskListTasksNavigationEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data object TaskListTasksContent :
    FonamentContent<TaskListTasksUIState, TaskListTasksContentState>() {

    @Composable
    override fun createContentState(
        uiState: TaskListTasksUIState,
    ): TaskListTasksContentState = rememberTaskListTasksContentState()

    @Composable
    override fun Content(
        uiState: TaskListTasksUIState,
        contentState: TaskListTasksContentState,
        modifier: Modifier,
    ) {
        when {
            contentState.showDeleteTaskAlertDialog -> {
                DeleteTaskAlertDialog(
                    onDeleteTask = {
                        onEvent(TaskListTasksEvent.DeleteTask(contentState.selectedTaskId))
                        onEvent(TaskListTasksEvent.CancelDeleteTaskAlertDialog)
                    },
                    onCancel = {
                        onEvent(TaskListTasksEvent.CancelDeleteTaskAlertDialog)
                    },
                )
            }

            contentState.showDeleteTaskListAlertDialog -> {
                DeleteTaskListAlertDialog(
                    onDeleteTaskList = {
                        onEvent(TaskListTasksEvent.DeleteTaskList)
                        onEvent(TaskListTasksNavigationEvent.NavigateBack)
                    },
                    onCancel = {
                        onEvent(TaskListTasksEvent.CancelDeleteTaskListAlertDialog)
                    },
                )
            }

            else -> {
                TaskListTasksScaffold(
                    scalingLazyListState = contentState.scalingLazyListState,
                    uiState = uiState,
                )
            }
        }
    }

    @Composable
    private fun TaskListTasksScaffold(
        scalingLazyListState: ScalingLazyListState,
        uiState: TaskListTasksUIState,
    ) {
        Scaffold(
            timeText = {
                if (uiState.tasksUIState is TasksUIState.Success) {
                    CurvedLayout {
                        curvedText(
                            text = TaskProgress.getPercentage(uiState.tasksUIState.progress),
                        )
                    }
                }
            },
            positionIndicator = {
                PositionIndicator(scalingLazyListState = scalingLazyListState)
            },
        ) {
            ScalingLazyColumn(
                autoCentering = AutoCenteringParams(itemIndex = 2),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                ),
                state = scalingLazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .onRotaryScrollEvent {
                        TaskListTasksEvent.LaunchRotaryScrollEvent(it)
                        true
                    }
                    .requestFocusOnHierarchyActive()
                    .focusable(),
            ) {
                taskListTitle(
                    taskListUIState = uiState.taskListUIState,
                )
                spacing()
                taskItems(
                    tasksUIState = uiState.tasksUIState,
                )
                spacing()
                taskListActions(
                    taskListUIState = uiState.taskListUIState,
                )
            }

            if (uiState.tasksUIState is TasksUIState.Success) {
                val progress = uiState.tasksUIState.progress
                val animatedProgress by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                )
                TaskListProgressIndicator(progress = { animatedProgress })
            }
        }
    }

    private fun ScalingLazyListScope.taskListTitle(
        taskListUIState: TaskListUIState,
    ) {
        when (taskListUIState) {
            is TaskListUIState.DefaultTaskList -> {
                item {
                    Text(
                        text = TodometerResources.strings.defaultTaskListName,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            is TaskListUIState.Success -> {
                item {
                    Text(
                        text = taskListUIState.taskList.name,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 24.dp),
                    )
                }
            }

            else -> Unit
        }
    }

    private fun ScalingLazyListScope.taskListActions(
        taskListUIState: TaskListUIState,
    ) {
        if (taskListUIState is TaskListUIState.DefaultTaskList || taskListUIState is TaskListUIState.Success) {
            item {
                AddTaskButton(
                    onComplete = { onEvent(TaskListTasksEvent.InsertTask(it)) },
                )
            }
        }
        if (taskListUIState is TaskListUIState.Success) {
            item {
                EditTaskListButton(
                    taskListUIState.taskList,
                    onComplete = { onEvent(TaskListTasksEvent.UpdateTaskListName(it)) },
                )
            }
            item {
                DeleteTaskListButton(
                    onClick = {
                        onEvent(TaskListTasksEvent.ShowDeleteTaskListAlertDialog)
                    },
                )
            }
        }
    }

    private fun ScalingLazyListScope.taskItems(
        tasksUIState: TasksUIState,
    ) {
        if (tasksUIState is TasksUIState.Success) {
            if (tasksUIState.tasks.isEmpty()) {
                item {
                    Text(
                        text = TodometerResources.strings.noTasks,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2,
                    )
                }
            } else {
                items(
                    tasksUIState.tasks,
                    key = { it.id },
                ) { task ->
                    TaskItem(
                        task,
                        onDoingClick = { onEvent(TaskListTasksEvent.SetTaskDoing(task.id)) },
                        onDoneClick = { onEvent(TaskListTasksEvent.SetTaskDone(task.id)) },
                        onDeleteTask = {
                            onEvent(TaskListTasksEvent.SelectTask(task.id))
                            onEvent(TaskListTasksEvent.ShowDeleteTaskAlertDialog)
                        },
                        onClick = {
                            onEvent(
                                TaskListTasksNavigationEvent.NavigateToTaskDetails(
                                    task.id,
                                ),
                            )
                        },
                    )
                }
            }
        }
    }

    private fun ScalingLazyListScope.spacing() {
        item { Spacer(modifier = Modifier.height(4.dp)) }
    }
}

@Composable
private fun TaskListProgressIndicator(progress: () -> Float) {
    CircularProgressIndicator(
        modifier = Modifier.fillMaxSize(),
        startAngle = 300f,
        endAngle = 240f,
        progress = progress.invoke(),
    )
}

@OptIn(
    ExperimentalWearFoundationApi::class,
    ExperimentalWearMaterialApi::class,
)
@Composable
private fun TaskItem(
    taskItem: TaskItem,
    onDoingClick: () -> Unit,
    onDoneClick: () -> Unit,
    onDeleteTask: () -> Unit,
    onClick: () -> Unit,
) {
    val revealState = rememberRevealState(
        anchors = createRevealAnchors(
            // TODO: Const values
            revealingAnchor = 0.5f,
            revealedAnchor = 0.5f,
        ),
    )
    SwipeToRevealChip(
        primaryAction = {
            TaskItemSwipeToRevealPrimaryAction(
                revealState = revealState,
                onClick = onDeleteTask,
            )
        },
        revealState = revealState,
        onFullSwipe = {},
    ) {
        TaskItemSplitToggleChip(
            title = taskItem.title,
            onClick = onClick,
            onDoingClick = onDoingClick,
            onDoneClick = onDoneClick,
            revealState = revealState,
            isTaskDone = taskItem.state == TaskState.DONE,
        )
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
private fun TaskItemSwipeToRevealPrimaryAction(
    revealState: RevealState,
    onClick: () -> Unit,
) {
    SwipeToRevealPrimaryAction(
        revealState = revealState,
        icon = {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = TodometerAnimatedResources.deleteAnimatedVectorPainter(
                        atEnd = revealState.currentValue == LeftRevealing,
                    ),
                    contentDescription = TodometerResources.strings.deleteTask,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        },
        label = { Text(TodometerResources.strings.deleteTask) },
        onClick = onClick,
    )
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
private fun TaskItemSplitToggleChip(
    title: String,
    onClick: () -> Unit,
    onDoingClick: () -> Unit,
    onDoneClick: () -> Unit,
    revealState: RevealState,
    isTaskDone: Boolean,
) {
    val textDecoration = if (isTaskDone) TextDecoration.LineThrough else TextDecoration.None
    // Use SplitToggleChip if onClick is needed.
    SplitToggleChip(
        checked = isTaskDone,
        onCheckedChange = { newValue ->
            if (newValue) {
                onDoneClick()
            } else {
                onDoingClick()
            }
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSurface,
                text = title,
                textDecoration = textDecoration,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onClick = onClick,
        toggleControl = {
            if (isTaskDone) {
                Icon(Images.Icons.TaskAlt, null)
            } else {
                Icon(Images.Icons.RadioButtonUnchecked, null)
            }
        },
        colors = ToggleChipDefaults.splitToggleChipColors(
            uncheckedToggleControlColor = MaterialTheme.colors.onSurface,
            checkedToggleControlColor = MaterialTheme.colors.primary,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = revealState.currentValue == Covered,
    )
}

@Composable
private fun AddTaskButton(onComplete: (String) -> Unit) {
    val taskTitleInput = TodometerResources.strings.taskTitleInput
    val addTaskLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val title = RemoteInput.getResultsFromIntent(result.data).getString(TaskTitle)
            title?.let { onComplete(it) }
        }
    }
    Chip(
        colors = ChipDefaults.gradientBackgroundChipColors(),
        icon = {
            Icon(Images.Icons.Add, null)
        },
        label = {
            Text(text = TodometerResources.strings.addTask)
        },
        onClick = {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: ImmutableList<RemoteInput> = persistentListOf(
                RemoteInput.Builder(TaskTitle)
                    .setLabel(taskTitleInput)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build(),
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            addTaskLauncher.launch(intent)
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun EditTaskListButton(
    taskList: TaskList,
    onComplete: (String) -> Unit,
) {
    val editTaskListLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val name = RemoteInput.getResultsFromIntent(result.data).getString(TaskListName)
                name?.let { onComplete(it) }
            }
        }
    Chip(
        colors = ChipDefaults.gradientBackgroundChipColors(),
        icon = {
            Icon(Images.Icons.Edit, null)
        },
        label = {
            Text(text = TodometerResources.strings.editTaskList)
        },
        onClick = {
            val intent: Intent =
                RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: ImmutableList<RemoteInput> = persistentListOf(
                RemoteInput.Builder(TaskListName)
                    .setLabel(taskList.name)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build(),
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            editTaskListLauncher.launch(intent)
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun DeleteTaskListButton(onClick: () -> Unit) {
    Chip(
        colors = ChipDefaults.primaryChipColors(
            backgroundColor = MaterialTheme.colors.error,
        ),
        icon = {
            Icon(
                Images.Icons.Delete,
                TodometerResources.strings.deleteTaskList,
            )
        },
        label = {
            Text(
                text = TodometerResources.strings.deleteTaskList,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    )
}

private const val TaskTitle = "task_title"
private const val TaskListName = "task_list_name"
