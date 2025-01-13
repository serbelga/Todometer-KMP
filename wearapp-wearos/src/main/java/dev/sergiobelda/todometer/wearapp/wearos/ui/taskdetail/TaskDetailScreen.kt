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

package dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetail

import android.app.Activity.RESULT_OK
import android.app.RemoteInput
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgument
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgumentType
import dev.sergiobelda.navigation.compose.extended.annotation.NavDestination
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Delete
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Edit
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.resources.TodometerResources
import dev.sergiobelda.todometer.wearapp.wearos.ui.deletetask.DeleteTaskAlertDialog
import dev.sergiobelda.todometer.wearapp.wearos.ui.loading.ContentLoadingProgress
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

// TODO: Resolve LongMethod issue.
@Suppress("LongMethod")
@NavDestination(
    destinationId = "taskdetail",
    name = "TaskDetail",
    arguments = [
        NavArgument("taskId", type = NavArgumentType.String),
    ],
    deepLinkUris = ["app://open.task"],
)
@OptIn(ExperimentalWearFoundationApi::class)
@Composable
internal fun TaskDetailScreen(
    taskId: String,
    navigateBack: () -> Unit,
    viewModel: TaskDetailViewModel = koinInject { parametersOf(taskId) },
) {
    val state = viewModel.state
    var deleteTaskAlertDialogState by remember { mutableStateOf(false) }

    when {
        deleteTaskAlertDialogState -> {
            DeleteTaskAlertDialog(
                onDeleteTask = {
                    viewModel.deleteTask()
                    navigateBack()
                },
                onCancel = { deleteTaskAlertDialogState = false },
            )
        }

        else -> {
            val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()

            Scaffold(
                positionIndicator = {
                    PositionIndicator(scalingLazyListState = scalingLazyListState)
                },
            ) {
                val focusRequester = rememberActiveFocusRequester()
                val coroutineScope = rememberCoroutineScope()

                ScalingLazyColumn(
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
                            coroutineScope.launch {
                                scalingLazyListState.scrollBy(it.verticalScrollPixels)

                                scalingLazyListState.animateScrollBy(0f)
                            }
                            true
                        }
                        .focusRequester(focusRequester)
                        .focusable(),
                ) {
                    when {
                        state.isLoading -> {
                            item { ContentLoadingProgress() }
                        }

                        else -> {
                            state.task?.let { task ->
                                item {
                                    Text(
                                        text = task.title,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2,
                                        modifier = Modifier.padding(horizontal = 24.dp),
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                                item {
                                    EditTaskButton(state.task) {
                                        viewModel.updateTask(
                                            it,
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
            Icon(Images.Icons.Edit, null)
        },
        label = {
            Text(text = TodometerResources.strings.editTask)
        },
        onClick = {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: ImmutableList<RemoteInput> = persistentListOf(
                RemoteInput.Builder(TaskTitle)
                    .setLabel(task.title)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build(),
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            launcher.launch(intent)
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun DeleteTaskButton(onClick: () -> Unit) {
    Chip(
        colors = ChipDefaults.primaryChipColors(
            backgroundColor = MaterialTheme.colors.error,
        ),
        icon = {
            Icon(Images.Icons.Delete, TodometerResources.strings.deleteTask)
        },
        label = {
            Text(text = TodometerResources.strings.deleteTask)
        },
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    )
}

private const val TaskTitle = "task_title"
