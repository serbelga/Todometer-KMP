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

package dev.sergiobelda.todometer.app.feature.edittasklist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLandscape
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLightDark
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.TodometerAppPreview
import dev.sergiobelda.todometer.app.feature.edittasklist.ui.EditTaskListContent
import dev.sergiobelda.todometer.app.feature.edittasklist.ui.EditTaskListUIState
import dev.sergiobelda.todometer.app.feature.edittasklist.ui.rememberEditTaskListContentState
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLocales

@PreviewLocales
@PreviewLightDark
@PreviewLandscape
@Composable
fun EditTaskListContentPreview() {
    TodometerAppPreview {
        EditTaskListContent(
            uiState = EditTaskListUIState(
                taskList = taskListSample,
            ),
            contentState = rememberEditTaskListContentState(
                taskListName = taskListSample.name,
            ),
        )
    }
}

@Preview
@Composable
fun EditTaskListLoadingPreview() {
    TodometerAppPreview {
        EditTaskListContent(
            uiState = EditTaskListUIState(
                isLoading = true,
            ),
            contentState = rememberEditTaskListContentState(
                taskListName = "",
            ),
        )
    }
}

private val taskListSample = TaskList(
    id = "1",
    name = "List of tasks",
    description = "",
    sync = true,
)
