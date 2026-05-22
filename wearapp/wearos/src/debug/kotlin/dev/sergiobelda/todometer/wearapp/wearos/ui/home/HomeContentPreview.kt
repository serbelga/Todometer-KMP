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

package dev.sergiobelda.todometer.wearapp.wearos.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview.TodometerWearAppPreview
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.preview.WearPreviewLocales
import dev.sergiobelda.todometer.wearapp.wearos.ui.tooling.util.taskListSample
import kotlinx.collections.immutable.persistentListOf

@WearPreviewDevices
@Composable
@PreviewWrapper(TodometerWearAppPreview::class)
fun HomeContentLoadingPreview() {
    HomeContent(
        uiState = HomeUIState.Loading,
        contentState = rememberHomeContentState(),
    )
}

@WearPreviewDevices
@Composable
@PreviewWrapper(TodometerWearAppPreview::class)
fun HomeContentPreview() {
    HomeContent(
        uiState =
            HomeUIState.Success(
                taskLists =
                    persistentListOf(
                        taskListSample,
                    ),
            ),
        contentState = rememberHomeContentState(),
    )
}

@WearPreviewLocales
@Composable
@PreviewWrapper(TodometerWearAppPreview::class)
fun HomeContentLocalesPreview() {
    HomeContent(
        uiState =
            HomeUIState.Success(
                taskLists = persistentListOf(),
            ),
        contentState = rememberHomeContentState(),
    )
}
