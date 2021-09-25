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

package com.sergiobelda.todometer.ui.taskdetail

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.data.doIfError
import com.sergiobelda.todometer.common.data.doIfSuccess
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.compose.mapper.composeColorOf
import com.sergiobelda.todometer.compose.ui.components.HorizontalDivider
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import com.sergiobelda.todometer.compose.ui.theme.TodometerTypography
import com.sergiobelda.todometer.ui.theme.ToDometerTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun TaskDetailScreen(
    taskId: String,
    editTask: (String) -> Unit,
    navigateUp: () -> Unit,
    taskDetailViewModel: TaskDetailViewModel = getViewModel()
) {
    val scrollState = rememberScrollState(0)
    val taskResultState = taskDetailViewModel.getTask(taskId).collectAsState()
    taskResultState.value.doIfError {
        navigateUp()
    }.doIfSuccess { task ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (scrollState.value >= 120) {
                            Text(task.title)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = { editTask(taskId) }) {
                            Icon(
                                Icons.Outlined.Edit,
                                contentDescription = "Edit task",
                                tint = TodometerColors.primary
                            )
                        }
                    },
                    elevation = 0.dp,
                    backgroundColor = TodometerColors.surface,
                    contentColor = contentColorFor(backgroundColor = TodometerColors.surface)
                )
            },
            content = {
                TaskDetailBody(scrollState, task)
            }
        )
    }
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
                            style = TodometerTypography.h6,
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
                    style = TodometerTypography.body1,
                    modifier = Modifier.padding(24.dp)
                )
            } else {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(id = R.string.no_description),
                        style = TodometerTypography.body1,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskDetailScreenPreview() {
    ToDometerTheme {
        TaskDetailScreen("", { _ -> }, {})
    }
}
