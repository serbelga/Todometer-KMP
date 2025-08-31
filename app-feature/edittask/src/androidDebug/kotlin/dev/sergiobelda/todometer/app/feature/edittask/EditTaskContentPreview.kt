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

package dev.sergiobelda.todometer.app.feature.edittask

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLandscape
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLightDark
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.TodometerAppPreview
import dev.sergiobelda.todometer.app.feature.edittask.ui.EditTaskContent
import dev.sergiobelda.todometer.app.feature.edittask.ui.EditTaskUIState
import dev.sergiobelda.todometer.app.feature.edittask.ui.rememberEditTaskContentState
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.ui.tooling.getTomorrowEpochMilliseconds
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLocales

@PreviewLocales
@PreviewLightDark
@PreviewLandscape
@Composable
fun EditTaskContentPreview() {
    TodometerAppPreview {
        EditTaskContent(
            uiState = EditTaskUIState(
                task = taskSample,
            ),
            contentState = rememberEditTaskContentState(
                taskSample,
            ),
        )
    }
}

@Preview
@Composable
fun EditTaskLoadingPreview() {
    TodometerAppPreview {
        EditTaskContent(
            uiState = EditTaskUIState(
                isLoading = true,
            ),
            contentState = rememberEditTaskContentState(
                taskSample,
            ),
        )
    }
}

@Preview
@Composable
fun EditTaskDeltaPreview() {
    val taskDelta = taskSample.copy(
        title = "",
        tag = Tag.UNSPECIFIED,
        dueDate = null,
        description = null,
    )
    TodometerAppPreview {
        EditTaskContent(
            uiState = EditTaskUIState(
                task = taskDelta,
            ),
            contentState = rememberEditTaskContentState(
                taskDelta,
            ),
        )
    }
}

private val taskSample = Task(
    id = "0",
    title = "Task title",
    description = LoremIpsum().values.first(),
    tag = Tag.BLUE,
    dueDate = getTomorrowEpochMilliseconds(),
    state = TaskState.DOING,
    taskListId = "0",
    isPinned = false,
    sync = false,
)
