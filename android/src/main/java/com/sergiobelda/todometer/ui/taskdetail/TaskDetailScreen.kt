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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
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
    var taskState: Task? by remember { mutableStateOf(null) }
    val taskResultState = taskDetailViewModel.getTask(taskId).observeAsState()
    taskResultState.value?.let { result ->
        result.doIfSuccess { taskState = it }
    }
    taskState?.let { task ->
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
                    elevation = 0.dp,
                    backgroundColor = TodometerColors.surface,
                    contentColor = contentColorFor(backgroundColor = TodometerColors.surface)
                )
            },
            content = {
                TaskDetailBody(scrollState, task)
            },
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = TodometerColors.primary,
                    onClick = {
                        /*editTask(taskId)*/
                    },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Edit task")
                }
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
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(TodometerColors.composeColorOf(task.tag))
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                        Text(
                            text = task.title,
                            style = TodometerTypography.h6,
                            modifier = Modifier.padding(20.dp),
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
