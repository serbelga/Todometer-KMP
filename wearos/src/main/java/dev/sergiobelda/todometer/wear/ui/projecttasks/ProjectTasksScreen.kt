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

package dev.sergiobelda.todometer.wear.ui.projecttasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.rememberScalingLazyListState
import org.koin.androidx.compose.getViewModel

@Composable
fun ProjectTasksScreen(
    projectTasksViewModel: ProjectTasksViewModel = getViewModel()
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    Scaffold {
        Column {
            ScalingLazyColumn(
                contentPadding = PaddingValues(
                    top = 28.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 40.dp
                ),
                state = scalingLazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    AddTaskButton()
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
                items(5) { index ->
                    // Use SplitToggleChip if you need an onClick.
                    ToggleChip(
                        //colors = ChipDefaults.secondaryChipColors(),
                        checked = true,
                        onCheckedChange = {},
                        label = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colors.onSurface,
                                text = "Task ${index + 1}"
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddTaskButton() {
    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        colors = ChipDefaults.secondaryChipColors(),
        icon = {
            Icon(
                Icons.Rounded.Add,
                contentDescription = "Add"
            )
        },
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Add Task"
            )
        },
        onClick = {
        }
    )
}
