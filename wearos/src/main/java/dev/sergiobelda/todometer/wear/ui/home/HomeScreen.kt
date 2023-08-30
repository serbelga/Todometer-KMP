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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.ToDometerSymbols
import dev.sergiobelda.todometer.common.resources.stringResource
import dev.sergiobelda.todometer.wear.ui.components.ToDometerLoadingProgress
import org.koin.androidx.compose.getViewModel

@Composable
internal fun HomeScreen(
    openTaskList: (String?) -> Unit,
    homeViewModel: HomeViewModel = getViewModel()
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    val homeUiState = homeViewModel.homeUiState

    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) }
    ) {
        ScalingLazyColumn(
            autoCentering = AutoCenteringParams(itemIndex = 1),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp
            ),
            state = scalingLazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item { ToDometerTitle() }
            item { Spacer(modifier = Modifier.height(4.dp)) }
            if (homeUiState.isLoading) {
                item { ToDometerLoadingProgress() }
            } else {
                item {
                    TaskListItem(
                        stringResource(MR.strings.default_task_list_name),
                        onClick = { openTaskList(null) }
                    )
                }
                items(homeUiState.taskLists) { taskList ->
                    TaskListItem(taskList.name) { openTaskList(taskList.id) }
                }
                item { Spacer(modifier = Modifier.height(4.dp)) }
                item {
                    AddTaskListButton { homeViewModel.insertTaskList(it) }
                }
            }
        }
    }
}

@Composable
private fun ToDometerTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            ToDometerSymbols.IsotypeDark,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Text(stringResource(MR.strings.app_name))
    }
}

@Composable
private fun TaskListItem(taskListName: String, onClick: () -> Unit) {
    Chip(
        colors = ChipDefaults.secondaryChipColors(),
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onSurface,
                text = taskListName
            )
        },
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AddTaskListButton(onComplete: (String) -> Unit) {
    val taskListNameInput = stringResource(MR.strings.task_list_name_input)
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val name = RemoteInput.getResultsFromIntent(result.data).getString(TaskListName)
                name?.let { onComplete(it) }
            }
        }
    Chip(
        colors = ChipDefaults.secondaryChipColors(),
        icon = {
            Icon(ToDometerIcons.Add, null)
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(MR.strings.add_task_list)
            )
        },
        onClick = {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: List<RemoteInput> = listOf(
                RemoteInput.Builder(TaskListName)
                    .setLabel(taskListNameInput)
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

private const val TaskListName = "task_list_name"
