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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import dev.sergiobelda.todometer.common.data.doIfError
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.wear.R

@Composable
fun TaskDetailScreen(
    editTask: () -> Unit,
    deleteTask: () -> Unit,
    taskDetailViewModel: TaskDetailViewModel
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    val taskResultState = taskDetailViewModel.task.collectAsState()
    Scaffold(
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) }
    ) {
        taskResultState.value.doIfSuccess { task ->
            ScalingLazyColumn(
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
                item {
                    Text(text = task.title)
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    EditTaskButton(editTask)
                }
                item {
                    DeleteTaskButton(deleteTask)
                }
            }
        }.doIfError {
            // TODO
        }
    }
}

@Composable
fun EditTaskButton(onClick: () -> Unit) {
    Chip(
        colors = ChipDefaults.secondaryChipColors(),
        icon = {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = stringResource(R.string.edit_task)
            )
        },
        label = {
            Text(text = stringResource(R.string.edit_task))
        },
        onClick = onClick
    )
}

@Composable
fun DeleteTaskButton(onClick: () -> Unit) {
    Chip(
        colors = ChipDefaults.secondaryChipColors(),
        icon = {
            Icon(
                Icons.Outlined.Delete,
                contentDescription = stringResource(R.string.delete_task)
            )
        },
        label = {
            Text(text = stringResource(R.string.delete_task))
        },
        onClick = onClick
    )
}
