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

package dev.sergiobelda.todometer.app.feature.addtasklist

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLandscape
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLightDark
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.TodometerAppPreview
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.AddTaskListContent
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.AddTaskListUIState
import dev.sergiobelda.todometer.app.feature.addtasklist.ui.rememberAddTaskListContentState
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLocales

@PreviewLocales
@PreviewLightDark
@PreviewLandscape
@Composable
fun AddTaskListContentPreview() {
    TodometerAppPreview {
        AddTaskListContent(
            uiState = AddTaskListUIState(),
            contentState = rememberAddTaskListContentState(),
        )
    }
}
