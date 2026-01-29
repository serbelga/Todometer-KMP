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

package dev.sergiobelda.todometer.wearapp.wearos.ui.tasklisttasks

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview.PreviewWearDevices
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview.PreviewWearLocales
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview.TodometerWearAppPreview
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.util.taskItemSample
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.util.taskListSample
import kotlinx.collections.immutable.persistentListOf

@PreviewWearDevices
@Composable
private fun TaskListTasksContentLoadingPreview() {
    TodometerWearAppPreview {
        TaskListTasksContent(
            uiState =
                TaskListTasksUIState(
                    taskListUIState = TaskListUIState.Loading,
                    tasksUIState = TasksUIState.Loading,
                ),
            contentState = rememberTaskListTasksContentState(),
        )
    }
}

@PreviewWearDevices
@PreviewWearLocales
@Composable
private fun TaskListTasksContentDefaultTaskListPreview() {
    TodometerWearAppPreview {
        TaskListTasksContent(
            uiState =
                TaskListTasksUIState(
                    taskListUIState = TaskListUIState.DefaultTaskList,
                    tasksUIState =
                        TasksUIState.Success(
                            tasks = persistentListOf(),
                        ),
                ),
            contentState = rememberTaskListTasksContentState(),
        )
    }
}

@PreviewWearDevices
@PreviewWearLocales
@Composable
private fun TaskListTasksContentPreview() {
    TodometerWearAppPreview {
        TaskListTasksContent(
            uiState =
                TaskListTasksUIState(
                    taskListUIState =
                        TaskListUIState.Success(
                            taskList = taskListSample,
                        ),
                    tasksUIState =
                        TasksUIState.Success(
                            tasks =
                                persistentListOf(
                                    taskItemSample,
                                ),
                        ),
                ),
            contentState = rememberTaskListTasksContentState(),
        )
    }
}
