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

package dev.sergiobelda.todometer.wear.ui.home

import android.app.Activity
import android.app.RemoteInput
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.wear.R
import org.koin.androidx.compose.getViewModel

private const val TASK_LIST_NAME = "task_list_name"

@Composable
fun HomeScreen(
    openTaskList: (String?) -> Unit,
    homeViewModel: HomeViewModel = getViewModel()
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    val taskListsResultState = homeViewModel.taskLists.collectAsState()
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val name = RemoteInput.getResultsFromIntent(result.data).getString(TASK_LIST_NAME)
                name?.let { homeViewModel.insertTaskList(it) }
            }
        }
    val taskListNameInput = stringResource(id = R.string.task_list_name_input)
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) }
    ) {
        ScalingLazyColumn(
            autoCentering = false,
            contentPadding = PaddingValues(
                top = 28.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 40.dp
            ),
            state = scalingLazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Text(stringResource(R.string.app_name)) }
            item { Spacer(modifier = Modifier.height(4.dp)) }
            taskListsResultState.value.doIfSuccess { taskLists ->
                //if (taskLists.isNullOrEmpty()) {
                //    item {
                //        Text(text = stringResource(id = R.string.no_task_lists))
                //    }
                //} else {
                    item {
                        TaskListItem("My tasks", onClick = { openTaskList(null) })
                    }
                    items(taskLists) { taskList ->
                        TaskListItem(taskList.name) { openTaskList(taskList.id) }
                    }
                //}
            }
            item { Spacer(modifier = Modifier.height(4.dp)) }
            item {
                AddTaskListButton {
                    val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
                    val remoteInputs: List<RemoteInput> = listOf(
                        RemoteInput.Builder(TASK_LIST_NAME)
                            .setLabel(taskListNameInput)
                            .wearableExtender {
                                setEmojisAllowed(false)
                                setInputActionType(EditorInfo.IME_ACTION_DONE)
                            }.build()
                    )

                    RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

                    launcher.launch(intent)
                }
            }
        }
    }
}

@Composable
fun TaskListItem(taskListName: String, onClick: () -> Unit) {
    Chip(
        colors = ChipDefaults.secondaryChipColors(),
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSurface,
                text = taskListName
            )
        },
        onClick = onClick
    )
}

@Composable
fun AddTaskListButton(onClick: () -> Unit) {
    Chip(
        modifier = Modifier.fillMaxWidth(),
        colors = ChipDefaults.secondaryChipColors(),
        icon = {
            Icon(Icons.Rounded.Add, null)
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.add_task_list)
            )
        },
        onClick = onClick
    )
}
