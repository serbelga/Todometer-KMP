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

package ui.taskdetail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.ToDometerContentLoadingProgress
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.compose.ui.taskdetail.taskTitle
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskUseCase
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.ui.error.mapToErrorUi
import koin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TaskDetailScreen(
    taskId: String,
    navigateBack: () -> Unit
) {
    val getTaskUseCase = koin.get<GetTaskUseCase>()

    val lazyListState = rememberLazyListState()
    var taskDetailUiState by mutableStateOf(TaskDetailUiState(isLoadingTask = true))
    val taskResultState by getTaskUseCase(taskId).collectAsState(null)

    taskResultState?.doIfSuccess { task ->
        taskDetailUiState = taskDetailUiState.copy(
            isLoadingTask = false,
            task = task,
            errorUi = null
        )
    }?.doIfError { error ->
        taskDetailUiState = taskDetailUiState.copy(
            isLoadingTask = false,
            task = null,
            errorUi = error.mapToErrorUi()
        )
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            stringResource(resource = MR.strings.back),
                            tint = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (taskDetailUiState.isLoadingTask) {
            ToDometerContentLoadingProgress()
        } else {
            taskDetailUiState.task?.let { task ->
                LazyColumn(state = lazyListState, modifier = Modifier.padding(paddingValues)) {
                    taskTitle(task)
                }
            }
        }
    }
}
