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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.CurvedLayout
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
import androidx.wear.compose.material.curvedText
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskProgress
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.wear.R

private const val TASK_TITLE = "task_title"
private const val TASK_LIST_NAME = "task_list_name"

@Composable
fun TaskListTasksScreen(
    openTask: (String) -> Unit,
    deleteTaskList: () -> Unit,
    taskListTasksViewModel: TaskListTasksViewModel
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    val tasksResultState = taskListTasksViewModel.tasks.collectAsState()
    val taskListResultState = taskListTasksViewModel.taskList.collectAsState()
    val addTaskLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val title = RemoteInput.getResultsFromIntent(result.data).getString(TASK_TITLE)
                title?.let { taskListTasksViewModel.insertTask(it) }
            }
        }
    val taskTitleInput = stringResource(id = R.string.task_title_input)
    val editTaskListLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val name = RemoteInput.getResultsFromIntent(result.data).getString(TASK_LIST_NAME)
                name?.let { taskListTasksViewModel.updateTaskListName(it) }
            }
        }
    var taskList: TaskList? = null
    taskListResultState.value.doIfSuccess { taskList = it }.doIfError { taskList = null }
    tasksResultState.value.doIfSuccess { tasks ->
        val progress = TaskProgress.getTasksDoneProgress(tasks)
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
                autoCentering = false,
                contentPadding = PaddingValues(
                    top = 32.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 50.dp
                ),
                state = scalingLazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                taskList?.let {
                    item { Text(it.name) }
                } ?: run {
                    item { Text(stringResource(R.string.default_task_list_name)) }
                }
                item { Spacer(modifier = Modifier.height(4.dp)) }
                if (tasks.isEmpty()) {
                    item { Text(text = stringResource(id = R.string.no_tasks)) }
                } else {
                    items(tasks) { task ->
                        TaskItem(
                            task,
                            onDoingClick = { taskListTasksViewModel.setTaskDoing(task.id) },
                            onDoneClick = { taskListTasksViewModel.setTaskDone(task.id) },
                            onClick = { openTask(task.id) }
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(4.dp)) }
                item {
                    AddTaskButton {
                        val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
                        val remoteInputs: List<RemoteInput> = listOf(
                            RemoteInput.Builder(TASK_TITLE)
                                .setLabel(taskTitleInput)
                                .wearableExtender {
                                    setEmojisAllowed(false)
                                    setInputActionType(EditorInfo.IME_ACTION_DONE)
                                }.build()
                        )

                        RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

                        addTaskLauncher.launch(intent)
                    }
                }
                if (taskList != null) {
                    item {
                        EditTaskListButton {
                            val intent: Intent =
                                RemoteInputIntentHelper.createActionRemoteInputIntent()
                            val remoteInputs: List<RemoteInput> = listOf(
                                RemoteInput.Builder(TASK_LIST_NAME)
                                    .setLabel(taskList?.name)
                                    .wearableExtender {
                                        setEmojisAllowed(false)
                                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                                    }.build()
                            )

                            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

                            editTaskListLauncher.launch(intent)
                        }
                    }
                    item { DeleteTaskListButton(deleteTaskList) }
                }
            }
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                startAngle = 300f,
                endAngle = 240f,
                progress = animatedProgress
            )
        }
    }.doIfError {
        // TODO
    }
}

@Composable
fun TaskItem(
    task: Task,
    onDoingClick: () -> Unit,
    onDoneClick: () -> Unit,
    onClick: () -> Unit
) {
    // Use SplitToggleChip if onClick is needed.
    SplitToggleChip(
        // colors = ChipDefaults.secondaryChipColors(),
        checked = task.state == TaskState.DONE,
        onCheckedChange = {
            if (task.state == TaskState.DOING) {
                onDoneClick()
            } else {
                onDoingClick()
            }
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSurface,
                text = task.title
            )
        },
        onClick = onClick
    )
}

@Composable
fun AddTaskButton(onClick: () -> Unit) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        colors = secondaryChipColors(),
        icon = {
            Icon(Icons.Rounded.Add, null)
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.add_task)
            )
        },
        onClick = onClick
    )
}

@Composable
fun EditTaskListButton(onClick: () -> Unit) {
    Chip(
        colors = secondaryChipColors(),
        icon = {
            Icon(Icons.Outlined.Edit, null)
        },
        label = {
            Text(text = stringResource(R.string.edit_task_list))
        },
        onClick = onClick
    )
}

@Composable
fun DeleteTaskListButton(onClick: () -> Unit) {
    Chip(
        colors = secondaryChipColors(),
        icon = {
            Icon(Icons.Outlined.Delete, null)
        },
        label = {
            Text(text = stringResource(R.string.delete_task_list))
        },
        onClick = onClick
    )
}
