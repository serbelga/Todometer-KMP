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

package dev.sergiobelda.todometer.wearapp.wearos.ui.home

import android.app.Activity
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
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
import dev.sergiobelda.navigation.compose.extended.annotation.NavDestination
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Add
import dev.sergiobelda.todometer.common.designsystem.resources.images.symbols.IsotypeCutDark
import dev.sergiobelda.todometer.common.resources.TodometerResources
import dev.sergiobelda.todometer.wearapp.wearos.ui.loading.ContentLoadingProgress
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@NavDestination(
    name = "Home",
    destinationId = "home",
)
@OptIn(ExperimentalWearFoundationApi::class)
@Composable
internal fun HomeScreen(
    openTaskList: (String?) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    val state = viewModel.state

    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
    ) {
        val focusRequester = rememberActiveFocusRequester()
        val coroutineScope = rememberCoroutineScope()

        ScalingLazyColumn(
            autoCentering = AutoCenteringParams(itemIndex = 1),
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
            item { ToDometerTitle() }
            item { Spacer(modifier = Modifier.height(4.dp)) }
            when {
                state.isLoading -> {
                    item { ContentLoadingProgress() }
                }

                else -> {
                    item {
                        TaskListItem(
                            TodometerResources.strings.defaultTaskListName,
                            onClick = { openTaskList(null) },
                        )
                    }
                    items(state.taskLists) { taskList ->
                        TaskListItem(taskList.name) { openTaskList(taskList.id) }
                    }
                    item { Spacer(modifier = Modifier.height(4.dp)) }
                    item {
                        AddTaskListButton { viewModel.insertTaskList(it) }
                    }
                }
            }
        }
    }
}

@Composable
private fun ToDometerTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            Images.Symbols.IsotypeCutDark,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colors.primary,
        )
        Text(
            TodometerResources.strings.appName,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
        )
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
                text = taskListName,
                overflow = TextOverflow.Ellipsis,
            )
        },
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun AddTaskListButton(onComplete: (String) -> Unit) {
    val taskListNameInput = TodometerResources.strings.taskListNameInput
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val name = RemoteInput.getResultsFromIntent(result.data).getString(TaskListName)
                name?.let { onComplete(it) }
            }
        }
    Chip(
        colors = ChipDefaults.gradientBackgroundChipColors(),
        icon = {
            Icon(Images.Icons.Add, null)
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = TodometerResources.strings.addTaskList,
            )
        },
        onClick = {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: ImmutableList<RemoteInput> = persistentListOf(
                RemoteInput.Builder(TaskListName)
                    .setLabel(taskListNameInput)
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

private const val TaskListName = "task_list_name"
