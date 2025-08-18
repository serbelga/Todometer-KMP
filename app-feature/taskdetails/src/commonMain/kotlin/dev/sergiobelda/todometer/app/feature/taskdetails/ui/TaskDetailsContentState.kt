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

package dev.sergiobelda.todometer.app.feature.taskdetails.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import dev.sergiobelda.todometer.common.ui.base.BaseContentState

@OptIn(ExperimentalMaterial3Api::class)
data class TaskDetailsContentState internal constructor(
    internal val lazyListState: LazyListState,
) : BaseContentState {

    internal val showTopAppBarTitle by derivedStateOf {
        lazyListState.firstVisibleItemIndex > 0
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun rememberTaskDetailsContentState(
    lazyListState: LazyListState = rememberLazyListState(),
): TaskDetailsContentState =
    TaskDetailsContentState(
        lazyListState = lazyListState,
    )
