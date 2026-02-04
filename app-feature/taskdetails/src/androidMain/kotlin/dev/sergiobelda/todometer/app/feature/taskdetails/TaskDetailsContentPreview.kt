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

package dev.sergiobelda.todometer.app.feature.taskdetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLandscape
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLightDark
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLocales
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.TodometerAppPreview
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.TaskDetailsContent
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.TaskDetailsUIState
import dev.sergiobelda.todometer.app.feature.taskdetails.ui.rememberTaskDetailsContentState
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItem
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.ui.tooling.getTomorrowEpochMilliseconds
import dev.sergiobelda.todometer.common.ui.tooling.getYesterdayEpochMilliseconds
import kotlinx.collections.immutable.persistentListOf

@PreviewLocales
@PreviewLightDark
@PreviewLandscape
@Composable
fun TaskDetailsContentPreview() {
    TodometerAppPreview {
        TaskDetailsContent(
            uiState =
                TaskDetailsUIState(
                    task = taskSample,
                    taskChecklistItems = taskChecklistItemsSample,
                ),
            contentState = rememberTaskDetailsContentState(),
        )
    }
}

@Preview
@Composable
fun TaskDetailsLoadingPreview() {
    TodometerAppPreview {
        TaskDetailsContent(
            uiState =
                TaskDetailsUIState(
                    isLoadingTask = true,
                ),
            contentState = rememberTaskDetailsContentState(),
        )
    }
}

@Preview
@Composable
fun TaskDetailsDeltaPreview() {
    TodometerAppPreview {
        TaskDetailsContent(
            uiState =
                TaskDetailsUIState(
                    task =
                        taskSample.copy(
                            title = LoremIpsum(30).values.first(),
                            isPinned = true,
                            tag = Tag.UNSPECIFIED,
                            dueDate = getYesterdayEpochMilliseconds(),
                            description = "",
                        ),
                ),
            contentState = rememberTaskDetailsContentState(),
        )
    }
}

private val taskSample =
    Task(
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

private val taskChecklistItemsSample =
    persistentListOf(
        TaskChecklistItem(
            id = "0",
            taskId = "0",
            text = "Item 1",
            state = TaskChecklistItemState.CHECKED,
        ),
        TaskChecklistItem(
            id = "1",
            taskId = "0",
            text = "Item 2",
            state = TaskChecklistItemState.UNCHECKED,
        ),
    )
