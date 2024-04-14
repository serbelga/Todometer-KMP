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

package dev.sergiobelda.todometer.app.feature.addtasklist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.app.common.designsystem.components.TodometerTitledTextField
import dev.sergiobelda.todometer.app.common.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.app.common.ui.components.SaveActionTopAppBar
import dev.sergiobelda.todometer.app.common.ui.values.TextFieldPadding
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Check
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.NavigateBefore
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
fun AddTaskListScreen(
    navigateBack: () -> Unit,
    viewModel: AddTaskListViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var taskListName by rememberSaveable { mutableStateOf("") }
    var taskListNameInputError by remember { mutableStateOf(false) }

    if (viewModel.uiState.isAdded) {
        navigateBack()
    }

    if (viewModel.uiState.errorUi != null) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = viewModel.uiState.errorUi?.message ?: ""
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            SaveActionTopAppBar(
                navigateBack = navigateBack,
                title = TodometerResources.strings.addTaskList,
                isSaveButtonEnabled = !viewModel.uiState.isAddingTaskList,
                onSaveButtonClick = {
                    if (taskListName.isBlank()) {
                        taskListNameInputError = true
                    } else {
                        viewModel.insertTaskList(taskListName)
                    }
                },
                saveButtonTintColor = if (viewModel.uiState.isAddingTaskList) {
                    MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha()
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        },
        content = { paddingValues ->
            AddTaskListContent(
                paddingValues = paddingValues,
                showProgress = viewModel.uiState.isAddingTaskList,
                taskListNameValue = taskListName,
                taskListNameInputError = taskListNameInputError,
                onTaskListNameValueChange = {
                    taskListName = it
                    taskListNameInputError = false
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskListTopBar(
    navigateBack: () -> Unit,
    isSaveButtonEnabled: Boolean,
    onSaveButtonClick: () -> Unit,
    saveButtonTintColor: Color = Color.Unspecified
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    Images.Icons.NavigateBefore,
                    contentDescription = TodometerResources.strings.back
                )
            }
        },
        title = { Text(TodometerResources.strings.addTaskList) },
        actions = {
            TextButton(
                enabled = isSaveButtonEnabled,
                onClick = onSaveButtonClick
            ) {
                Icon(
                    Images.Icons.Check,
                    contentDescription = TodometerResources.strings.save,
                    tint = saveButtonTintColor
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = TodometerResources.strings.save,
                    style = MaterialTheme.typography.bodyMedium,
                    color = saveButtonTintColor
                )
            }
        }
    )
}

@Composable
private fun AddTaskListContent(
    paddingValues: PaddingValues,
    showProgress: Boolean,
    taskListNameValue: String,
    taskListNameInputError: Boolean,
    onTaskListNameValueChange: (String) -> Unit
) {
    if (showProgress) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
    Column(modifier = Modifier.padding(paddingValues)) {
        TodometerTitledTextField(
            title = TodometerResources.strings.name,
            value = taskListNameValue,
            onValueChange = onTaskListNameValueChange,
            placeholder = { Text(TodometerResources.strings.enter_task_list_name) },
            singleLine = true,
            isError = taskListNameInputError,
            errorMessage = TodometerResources.strings.field_not_empty,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.padding(TextFieldPadding)
        )
    }
}
