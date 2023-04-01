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

package dev.sergiobelda.todometer.wear.ui.tasklisttasks

import android.app.Activity
import android.app.RemoteInput
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.CurvedLayout
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults.secondaryChipColors
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.ProgressIndicatorDefaults
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.SplitToggleChip
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChipDefaults
import androidx.wear.compose.material.curvedText
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.painterResource
import dev.sergiobelda.todometer.common.resources.stringResource
import dev.sergiobelda.todometer.common.ui.task.TaskProgress
import dev.sergiobelda.todometer.wear.ui.components.ToDometerLoadingProgress
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun TaskListTasksScreen(
    taskListId: String,
    openTask: (String) -> Unit,
    deleteTaskList: () -> Unit,
    taskListTasksViewModel: TaskListTasksViewModel = getViewModel { parametersOf(taskListId) }
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    val taskListTasksUiState = taskListTasksViewModel.taskListTasksUiState
    val progress = TaskProgress.getTasksDoneProgress(taskListTasksUiState.tasks)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    Scaffold(
        timeText = {
            CurvedLayout { curvedText(text = TaskProgress.getPercentage(progress)) }
        },
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) }
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
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!taskListTasksUiState.isLoadingTaskList) {
                when {
                    taskListTasksUiState.taskList == null && taskListTasksUiState.isDefaultTaskList ->
                        item { Text(stringResource(MR.strings.default_task_list_name)) }
                    taskListTasksUiState.taskList != null ->
                        item { Text(taskListTasksUiState.taskList.name) }
                }
            }
            item { Spacer(modifier = Modifier.height(4.dp)) }
            when {
                taskListTasksUiState.isLoadingTasks -> {
                    item { ToDometerLoadingProgress() }
                }
                taskListTasksUiState.tasks.isEmpty() -> {
                    item { Text(text = stringResource(MR.strings.no_tasks)) }
                }
                else -> {
                    items(taskListTasksUiState.tasks, key = { it.id }) { task ->
                        TaskItem(
                            task,
                            onDoingClick = { taskListTasksViewModel.setTaskDoing(task.id) },
                            onDoneClick = { taskListTasksViewModel.setTaskDone(task.id) },
                            onClick = { openTask(task.id) }
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(4.dp)) }
            if (!taskListTasksUiState.isLoadingTaskList) {
                when {
                    taskListTasksUiState.taskList == null && taskListTasksUiState.isDefaultTaskList -> {
                        item {
                            AddTaskButton { taskListTasksViewModel.insertTask(it) }
                        }
                    }
                    taskListTasksUiState.taskList != null -> {
                        item {
                            AddTaskButton { taskListTasksViewModel.insertTask(it) }
                        }
                        item {
                            EditTaskListButton(taskListTasksUiState.taskList) {
                                taskListTasksViewModel.updateTaskListName(it)
                            }
                        }
                        item { DeleteTaskListButton(deleteTaskList) }
                    }
                }
            }
        }
        TaskListProgressIndicator(progress = animatedProgress)
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

@Composable
private fun TaskItem(
    taskItem: TaskItem,
    onDoingClick: () -> Unit,
    onDoneClick: () -> Unit,
    onClick: () -> Unit
) {
    val isTaskDone = taskItem.state == TaskState.DONE
    val textDecoration = if (isTaskDone) TextDecoration.LineThrough else TextDecoration.None
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
                textDecoration = textDecoration
            )
        },
        onClick = onClick,
        toggleControl = {
            if (isTaskDone) {
                Icon(painterResource(ToDometerIcons.Replay), null)
            } else {
                Icon(painterResource(ToDometerIcons.Check), null)
            }
        },
        colors = ToggleChipDefaults.splitToggleChipColors(uncheckedToggleControlColor = MaterialTheme.colors.secondary),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AddTaskButton(onComplete: (String) -> Unit) {
    val taskTitleInput = stringResource(MR.strings.task_title_input)
    val addTaskLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val title = RemoteInput.getResultsFromIntent(result.data).getString(TaskTitle)
                title?.let { onComplete(it) }
            }
        }
    Chip(
        colors = secondaryChipColors(),
        icon = {
            Icon(painterResource(ToDometerIcons.Add), null)
        },
        label = {
            Text(text = stringResource(MR.strings.add_task))
        },
        onClick = {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: List<RemoteInput> = listOf(
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
        colors = secondaryChipColors(),
        icon = {
            Icon(painterResource(ToDometerIcons.Edit), null)
        },
        label = {
            Text(text = stringResource(MR.strings.edit_task_list))
        },
        onClick = {
            val intent: Intent =
                RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: List<RemoteInput> = listOf(
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
        colors = secondaryChipColors(),
        icon = {
            Icon(
                painterResource(ToDometerIcons.Delete),
                stringResource(MR.strings.delete_task_list)
            )
        },
        label = {
            Text(text = stringResource(MR.strings.delete_task_list))
        },
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    )
}

private const val TaskTitle = "task_title"
private const val TaskListName = "task_list_name"
