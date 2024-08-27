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

package dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks

import android.app.Activity
import android.app.RemoteInput
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.RevealValue.Companion.Covered
import androidx.wear.compose.foundation.RevealValue.Companion.Revealing
import androidx.wear.compose.foundation.createAnchors
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.foundation.rememberRevealState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.ProgressIndicatorDefaults
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.SplitToggleChip
import androidx.wear.compose.material.SwipeToRevealChip
import androidx.wear.compose.material.SwipeToRevealPrimaryAction
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChipDefaults
import androidx.wear.compose.material.curvedText
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgument
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgumentType
import dev.sergiobelda.navigation.compose.extended.annotation.NavDestination
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
import dev.sergiobelda.todometer.wearapp.wearos.ui.loading.ContentLoadingProgress
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@NavDestination(
    destinationId = "tasklisttasks",
    name = "TaskListTasks",
    arguments = [
        NavArgument("taskListId", type = NavArgumentType.String, nullable = true)
    ]
)
@OptIn(ExperimentalWearFoundationApi::class)
@Composable
internal fun TaskListTasksScreen(
    taskListId: String,
    openTask: (String) -> Unit,
    navigateBack: () -> Unit,
    viewModel: TaskListTasksViewModel = koinInject { parametersOf(taskListId) }
) {
    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }
    var deleteTaskListAlertDialogState by remember { mutableStateOf(false) }
    var selectedTaskId by remember { mutableStateOf("") }
    when {
        deleteTaskAlertDialogState -> {
            DeleteTaskAlertDialog(
                onDeleteTask = {
                    viewModel.deleteTask(selectedTaskId)
                    deleteTaskAlertDialogState = false
                },
                onCancel = { deleteTaskAlertDialogState = false }
            )
        }

        deleteTaskListAlertDialogState -> {
            DeleteTaskListAlertDialog(
                onDeleteTaskList = {
                    viewModel.deleteTaskList()
                    navigateBack()
                },
                onCancel = { deleteTaskListAlertDialogState = false }
            )
        }

        else -> {
            val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
            val state = viewModel.state
            val focusRequester = rememberActiveFocusRequester()
            val coroutineScope = rememberCoroutineScope()
            val progress = TaskProgress.getTasksDoneProgress(state.tasks)
            val animatedProgress by animateFloatAsState(
                targetValue = progress,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
            )
            Scaffold(
                timeText = {
                    CurvedLayout { curvedText(text = TaskProgress.getPercentage(progress)) }
                },
                positionIndicator = {
                    PositionIndicator(scalingLazyListState = scalingLazyListState)
                }
            ) {
                ScalingLazyColumn(
                    autoCentering = AutoCenteringParams(itemIndex = 2),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp
                    ),
                    state = scalingLazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onRotaryScrollEvent {
                            coroutineScope.launch {
                                scalingLazyListState.scrollBy(it.verticalScrollPixels)

                                scalingLazyListState.animateScrollBy(0f)
                            }
                            true
                        }
                        .focusRequester(focusRequester)
                        .focusable()
                ) {
                    when {
                        state.isLoadingTaskList -> {
                            item { ContentLoadingProgress() }
                        }

                        !state.isLoadingTaskList -> {
                            when {
                                state.taskList == null && state.isDefaultTaskList -> {
                                    item {
                                        Text(
                                            TodometerResources.strings.default_task_list_name,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                state.taskList != null -> {
                                    item {
                                        Text(
                                            state.taskList.name,
                                            fontWeight = FontWeight.Bold,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                            modifier = Modifier.padding(horizontal = 24.dp)
                                        )
                                    }
                                }
                            }
                            item { Spacer(modifier = Modifier.height(4.dp)) }
                            if (state.tasks.isEmpty()) {
                                item {
                                    Text(
                                        text = TodometerResources.strings.no_tasks,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.body2
                                    )
                                }
                            } else {
                                items(state.tasks, key = { it.id }) { task ->
                                    TaskItem(
                                        task,
                                        onDoingClick = { viewModel.setTaskDoing(task.id) },
                                        onDoneClick = { viewModel.setTaskDone(task.id) },
                                        onDeleteTask = {
                                            // TODO: Refactor this assignment
                                            selectedTaskId = task.id
                                            deleteTaskAlertDialogState = true
                                        },
                                        onClick = { openTask(task.id) }
                                    )
                                }
                            }
                            item { Spacer(modifier = Modifier.height(4.dp)) }
                            item {
                                AddTaskButton { viewModel.insertTask(it) }
                            }
                            if (state.taskList != null) {
                                item {
                                    EditTaskListButton(state.taskList) {
                                        viewModel.updateTaskListName(it)
                                    }
                                }
                                item {
                                    DeleteTaskListButton(
                                        onClick = {
                                            deleteTaskListAlertDialogState = true
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                TaskListProgressIndicator(progress = animatedProgress)
            }
        }
    }
}

@Composable
private fun TaskListProgressIndicator(progress: Float) {
    CircularProgressIndicator(
        modifier = Modifier.fillMaxSize(),
        startAngle = 300f,
        endAngle = 240f,
        progress = progress
    )
}

@OptIn(
    ExperimentalWearFoundationApi::class,
    ExperimentalWearMaterialApi::class
)
@Composable
private fun TaskItem(
    taskItem: TaskItem,
    onDoingClick: () -> Unit,
    onDoneClick: () -> Unit,
    onDeleteTask: () -> Unit,
    onClick: () -> Unit
) {
    val isTaskDone = taskItem.state == TaskState.DONE
    val textDecoration = if (isTaskDone) TextDecoration.LineThrough else TextDecoration.None
    val revealState = rememberRevealState(
        anchors = createAnchors(
            // TODO: Const values
            revealingAnchor = 0.5f,
            revealedAnchor = 0.5f
        )
    )
    SwipeToRevealChip(
        primaryAction = {
            SwipeToRevealPrimaryAction(
                revealState = revealState,
                icon = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Icon(
                            painter = TodometerAnimatedResources.deleteAnimatedVectorPainter(
                                atEnd = revealState.currentValue == Revealing
                            ),
                            contentDescription = TodometerResources.strings.delete_task,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                label = { Text(TodometerResources.strings.delete_task) },
                onClick = onDeleteTask
            )
        },
        revealState = revealState,
        onFullSwipe = {}
    ) {
        // Use SplitToggleChip if onClick is needed.
        SplitToggleChip(
            checked = isTaskDone,
            onCheckedChange = {
                if (isTaskDone) {
                    onDoingClick()
                } else {
                    onDoneClick()
                }
            },
            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onSurface,
                    text = taskItem.title,
                    textDecoration = textDecoration,
                    overflow = TextOverflow.Ellipsis
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
                checkedToggleControlColor = MaterialTheme.colors.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = revealState.currentValue == Covered
        )
    }
}

@Composable
private fun AddTaskButton(onComplete: (String) -> Unit) {
    val taskTitleInput = TodometerResources.strings.task_title_input
    val addTaskLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
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
                    }.build()
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            addTaskLauncher.launch(intent)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun EditTaskListButton(taskList: TaskList, onComplete: (String) -> Unit) {
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
            Text(text = TodometerResources.strings.edit_task_list)
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
                    }.build()
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            editTaskListLauncher.launch(intent)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun DeleteTaskListButton(onClick: () -> Unit) {
    Chip(
        colors = ChipDefaults.primaryChipColors(
            backgroundColor = MaterialTheme.colors.error
        ),
        icon = {
            Icon(
                Images.Icons.Delete,
                TodometerResources.strings.delete_task_list
            )
        },
        label = {
            Text(
                text = TodometerResources.strings.delete_task_list,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    )
}

private const val TaskTitle = "task_title"
private const val TaskListName = "task_list_name"
