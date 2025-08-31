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

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview.PreviewWearDevices
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview.PreviewWearLocales
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview.TodometerWearAppPreview

@PreviewWearDevices
@Composable
fun TaskDetailsLoadingPreview() {
    TodometerWearAppPreview {
        TaskDetailsContent(
            uiState = TaskDetailsUIState.LoadingTaskDetailsUIState,
            contentState = rememberTaskDetailsContentState(),
        )
    }
}

@PreviewWearDevices
@Composable
fun TaskDetailsContentPreview() {
    TodometerWearAppPreview {
        TaskDetailsContent(
            uiState = TaskDetailsUIState.SuccessTaskDetailsUIState(
                task = taskSample,
            ),
            contentState = rememberTaskDetailsContentState(),
        )
    }
}

@PreviewWearLocales
@Composable
fun TaskDetailsContentLocalesPreview() {
    TodometerWearAppPreview {
        TaskDetailsContent(
            uiState = TaskDetailsUIState.SuccessTaskDetailsUIState(
                task = taskSample,
            ),
            contentState = rememberTaskDetailsContentState(),
        )
    }
}

private val taskSample = Task(
    id = "0",
    title = "The task title",
    description = "",
    tag = Tag.UNSPECIFIED,
    dueDate = 0,
    state = TaskState.DOING,
    taskListId = "0",
    isPinned = false,
    sync = false,
)
