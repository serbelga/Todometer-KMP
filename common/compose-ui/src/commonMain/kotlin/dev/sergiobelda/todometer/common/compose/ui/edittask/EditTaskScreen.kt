/*
 * Copyright 2023 Sergio Belda
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

package dev.sergiobelda.todometer.common.compose.ui.edittask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerContentLoadingProgress
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerDateTimeSelector
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerTagSelector
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.HorizontalDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.TitledTextField
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    navigateBack: () -> Unit,
    updateTask: (taskTitle: String, selectedTag: Tag, taskDescription: String, taskDueDate: Long?) -> Unit,
    editTaskUiState: EditTaskUiState
) {
    var taskTitle by rememberSaveable { mutableStateOf("") }
    var taskTitleInputError: Boolean by remember { mutableStateOf(false) }
    var taskDescription by rememberSaveable { mutableStateOf("") }
    var selectedTag by rememberSaveable { mutableStateOf(Tag.GRAY) }
    var taskDueDate: Long? by rememberSaveable { mutableStateOf(null) }

    editTaskUiState.task?.let { task ->
        taskTitle = task.title
        taskDescription = task.description ?: ""
        selectedTag = task.tag
        taskDueDate = task.dueDate
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
                        )
                    }
                },
                actions = {
                    if (!editTaskUiState.isLoading && editTaskUiState.task != null) {
                        IconButton(
                            onClick = {
                                if (taskTitle.isBlank()) {
                                    taskTitleInputError = true
                                } else {
                                    updateTask(taskTitle, selectedTag, taskDescription, taskDueDate)
                                    navigateBack()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Rounded.Check,
                                contentDescription = "Save",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                title = { Text(stringResource(MR.strings.edit_task)) }
            )
        },
        content = { paddingValues ->
            if (editTaskUiState.isLoading) {
                ToDometerContentLoadingProgress()
            } else {
                Column(modifier = Modifier.padding(paddingValues)) {
                    TitledTextField(
                        title = stringResource(MR.strings.name),
                        value = taskTitle,
                        onValueChange = {
                            taskTitle = it
                            taskTitleInputError = false
                        },
                        placeholder = { Text(stringResource(MR.strings.enter_task_name)) },
                        isError = taskTitleInputError,
                        errorMessage = stringResource(MR.strings.field_not_empty),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        )
                    )
                    ToDometerTagSelector(selectedTag) { tag ->
                        selectedTag = tag
                    }
                    ToDometerDateTimeSelector(
                        taskDueDate,
                        onDateTimeSelected = { taskDueDate = it },
                        onClearDateTimeClick = { taskDueDate = null }
                    )
                    TitledTextField(
                        title = stringResource(MR.strings.description),
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        placeholder = { Text(stringResource(MR.strings.enter_description)) },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        ),
                        maxLines = 4
                    )
                    HorizontalDivider()
                }
            }
        }
    )
}
