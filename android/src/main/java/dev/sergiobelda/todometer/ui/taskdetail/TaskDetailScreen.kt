/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.ui.taskdetail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.compose.ui.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.mapper.composeColorOf
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerTypography
import dev.sergiobelda.todometer.common.compose.ui.theme.onSurfaceMediumEmphasis
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.ui.components.ToDometerContentLoadingProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    editTask: () -> Unit,
    navigateUp: () -> Unit,
    taskDetailViewModel: TaskDetailViewModel
) {
    val scrollState = rememberScrollState(0)
    val taskDetailUiState = taskDetailViewModel.taskDetailUiState
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    if (scrollState.value >= 120) {
                        if (!taskDetailUiState.isLoading && taskDetailUiState.task != null) {
                            Text(taskDetailUiState.task.title)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = TodometerColors.onSurfaceMediumEmphasis
                        )
                    }
                },
                actions = {
                    if (!taskDetailUiState.isLoading && taskDetailUiState.task != null) {
                        IconButton(onClick = editTask) {
                            Icon(
                                Icons.Outlined.Edit,
                                contentDescription = "Edit task",
                                tint = TodometerColors.primary
                            )
                        }
                    }
                }
            )
        },
        content = {
            if (taskDetailUiState.isLoading) {
                ToDometerContentLoadingProgress()
            } else {
                taskDetailUiState.task?.let { task ->
                    TaskDetailBody(scrollState, task)
                }
            }
        }
    )
}

@Composable
fun TaskDetailBody(scrollState: ScrollState, task: Task) {
    Column {
        if (scrollState.value >= 270) {
            HorizontalDivider()
        }
        Column(modifier = Modifier.verticalScroll(state = scrollState)) {
            Surface(
                modifier = Modifier.height(64.dp)
            ) {
                Row(
                    modifier = Modifier.padding(start = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(TodometerColors.composeColorOf(task.tag))
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        Text(
                            text = task.title,
                            style = TodometerTypography.headlineSmall,
                            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                            maxLines = 1
                        )
                    }
                }
            }
            HorizontalDivider()
            if (!task.description.isNullOrBlank()) {
                Text(
                    text = task.description ?: "",
                    style = TodometerTypography.bodyLarge,
                    modifier = Modifier.padding(24.dp)
                )
            } else {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(id = R.string.no_description),
                        style = TodometerTypography.bodyLarge,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
}
