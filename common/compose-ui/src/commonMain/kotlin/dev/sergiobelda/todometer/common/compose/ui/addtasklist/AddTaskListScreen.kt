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

package dev.sergiobelda.todometer.common.compose.ui.addtasklist

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
import dev.sergiobelda.todometer.common.compose.ui.components.SaveActionTopAppBar
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.ToDometerTitledTextField
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.common.compose.ui.values.TextFieldPadding
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskListScreen(
    navigateBack: () -> Unit,
    insertTaskList: (String) -> Unit,
    addTaskListUiState: AddTaskListUiState
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var taskListName by rememberSaveable { mutableStateOf("") }
    var taskListNameInputError by remember { mutableStateOf(false) }

    if (addTaskListUiState.isAdded) {
        navigateBack()
    }

    if (addTaskListUiState.errorUi != null) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = addTaskListUiState.errorUi.message ?: ""
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            SaveActionTopAppBar(
                navigateBack = navigateBack,
                title = stringResource(MR.strings.add_task_list),
                isSaveButtonEnabled = !addTaskListUiState.isAddingTaskList,
                onSaveButtonClick = {
                    if (taskListName.isBlank()) {
                        taskListNameInputError = true
                    } else {
                        insertTaskList(taskListName)
                    }
                },
                saveButtonTintColor = if (addTaskListUiState.isAddingTaskList) {
                    MaterialTheme.colorScheme.onSurface.applyMediumEmphasisAlpha()
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        },
        content = { paddingValues ->
            AddTaskListContent(
                paddingValues = paddingValues,
                showProgress = addTaskListUiState.isAddingTaskList,
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
                    ToDometerIcons.ArrowBack,
                    contentDescription = stringResource(MR.strings.back)
                )
            }
        },
        title = { Text(stringResource(MR.strings.add_task_list)) },
        actions = {
            TextButton(
                enabled = isSaveButtonEnabled,
                onClick = onSaveButtonClick
            ) {
                Icon(
                    ToDometerIcons.Check,
                    contentDescription = stringResource(MR.strings.save),
                    tint = saveButtonTintColor
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(MR.strings.save),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.alignByBaseline(),
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
        ToDometerTitledTextField(
            title = stringResource(MR.strings.name),
            value = taskListNameValue,
            onValueChange = onTaskListNameValueChange,
            placeholder = { Text(stringResource(MR.strings.enter_task_list_name)) },
            singleLine = true,
            isError = taskListNameInputError,
            errorMessage = stringResource(MR.strings.field_not_empty),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.padding(TextFieldPadding)
        )
    }
}
