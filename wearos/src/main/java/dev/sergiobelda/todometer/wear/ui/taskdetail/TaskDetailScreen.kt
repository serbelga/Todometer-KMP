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

package dev.sergiobelda.todometer.wear.ui.taskdetail

import android.app.Activity.RESULT_OK
import android.app.RemoteInput
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import dev.sergiobelda.todometer.common.designsystem.resources.TodometerIcons
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource
import dev.sergiobelda.todometer.wear.ui.deletetask.DeleteTaskAlertDialog
import dev.sergiobelda.todometer.wear.ui.loading.ContentLoadingProgress
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun TaskDetailScreen(
    taskId: String,
    navigateBack: () -> Unit,
    taskDetailViewModel: TaskDetailViewModel = getViewModel(parameters = { parametersOf(taskId) })
) {
    val taskDetailUiState = taskDetailViewModel.taskDetailUiState
    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }

    when {
        deleteTaskAlertDialogState -> {
            DeleteTaskAlertDialog(
                onDeleteTask = {
                    taskDetailViewModel.deleteTask()
                    navigateBack()
                },
                onCancel = { deleteTaskAlertDialogState = false }
            )
        }

        else -> {
            val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
            val focusRequester = remember { FocusRequester() }
            val coroutineScope = rememberCoroutineScope()

            Scaffold(
                positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) }
            ) {
                LaunchedEffect(Unit) { focusRequester.requestFocus() }
                ScalingLazyColumn(
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
                            }
                            true
                        }
                        .focusRequester(focusRequester)
                        .focusable()
                ) {
                    when {
                        taskDetailUiState.isLoading -> {
                            item { ContentLoadingProgress() }
                        }

                        else -> {
                            taskDetailUiState.task?.let { task ->
                                item {
                                    Text(text = task.title, fontWeight = FontWeight.Bold)
                                }
                                item {
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                                item {
                                    EditTaskButton(taskDetailUiState.task) {
                                        taskDetailViewModel.updateTask(
                                            it
                                        )
                                    }
                                }
                                item {
                                    DeleteTaskButton { deleteTaskAlertDialogState = true }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EditTaskButton(task: Task, onComplete: (String) -> Unit) {
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val title = RemoteInput.getResultsFromIntent(result.data).getString(TaskTitle)
                if (!title.isNullOrEmpty()) {
                    onComplete(title)
                }
            }
        }

    Chip(
        colors = ChipDefaults.gradientBackgroundChipColors(),
        icon = {
            Icon(TodometerIcons.Edit, null)
        },
        label = {
            Text(text = stringResource(MR.strings.edit_task))
        },
        onClick = {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: List<RemoteInput> = listOf(
                RemoteInput.Builder(TaskTitle)
                    .setLabel(task.title)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build()
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            launcher.launch(intent)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun DeleteTaskButton(onClick: () -> Unit) {
    Chip(
        colors = ChipDefaults.primaryChipColors(
            backgroundColor = MaterialTheme.colors.error
        ),
        icon = {
            Icon(TodometerIcons.Delete, stringResource(MR.strings.delete_task))
        },
        label = {
            Text(text = stringResource(MR.strings.delete_task))
        },
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    )
}

private const val TaskTitle = "task_title"
