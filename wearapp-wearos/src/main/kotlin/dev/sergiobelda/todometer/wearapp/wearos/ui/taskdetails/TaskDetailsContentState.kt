/*
 * Copyright 2025 Sergio Belda
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

package dev.sergiobelda.todometer.wearapp.wearos.ui.taskdetails

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import dev.sergiobelda.fonament.ui.FonamentContentState
import dev.sergiobelda.fonament.ui.FonamentEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
data class TaskDetailsContentState internal constructor(
    val scalingLazyListState: ScalingLazyListState,
    val coroutineScope: CoroutineScope,
) : FonamentContentState {

    var showDeleteTaskAlertDialog by mutableStateOf(false)
        private set

    override fun handleEvent(event: FonamentEvent) {
        when (event) {
            TaskDetailsEvent.ShowDeleteTaskAlertDialog -> {
                showDeleteTaskAlertDialog = true
            }
            TaskDetailsEvent.CancelDeleteTaskAlertDialog -> {
                showDeleteTaskAlertDialog = false
            }
            is TaskDetailsEvent.LaunchRotaryScrollEvent -> {
                coroutineScope.launch {
                    scalingLazyListState.scrollBy(event.scrollEvent.verticalScrollPixels)

                    scalingLazyListState.animateScrollBy(0f)
                }
            }
        }
    }
}

@Composable
fun rememberTaskDetailsContentState(
    scalingLazyListState: ScalingLazyListState = rememberScalingLazyListState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): TaskDetailsContentState = remember {
    TaskDetailsContentState(
        scalingLazyListState = scalingLazyListState,
        coroutineScope = coroutineScope,
    )
}
