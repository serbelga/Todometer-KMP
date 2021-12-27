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

package dev.sergiobelda.todometer.wear.ui.edittask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import dev.sergiobelda.todometer.common.data.doIfError
import dev.sergiobelda.todometer.common.data.doIfSuccess

@Composable
fun EditTaskScreen(
    editTaskViewModel: EditTaskViewModel
) {
    val scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState()
    val taskResultState = editTaskViewModel.task.collectAsState()
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
                item { Text(text = task.title) }
            }
        }.doIfError {
            // TODO
        }
    }
}
