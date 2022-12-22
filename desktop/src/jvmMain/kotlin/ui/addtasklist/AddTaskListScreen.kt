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

package ui.addtasklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.TitledTextField
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.resources.MR
import koin
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTaskListScreen(navigateBack: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val addTaskListViewModel: AddTaskListViewModel = remember {
        koin.get { parametersOf(coroutineScope) }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    var taskListName by rememberSaveable { mutableStateOf("") }
    var taskListNameInputError by remember { mutableStateOf(false) }

    if (addTaskListViewModel.addTaskListUiState.isAdded) {
        navigateBack()
    }

    if (addTaskListViewModel.addTaskListUiState.errorUi != null) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = addTaskListViewModel.addTaskListUiState.errorUi?.message ?: ""
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                title = { Text(stringResource(resource = MR.strings.add_task_list)) },
                actions = {
                    IconButton(
                        enabled = !addTaskListViewModel.addTaskListUiState.isAddingTaskList,
                        onClick = {
                            if (taskListName.isBlank()) {
                                taskListNameInputError = true
                            } else {
                                addTaskListViewModel.insertTaskList(taskListName)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = "Save",
                            tint = if (addTaskListViewModel.addTaskListUiState.isAddingTaskList) ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis else MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            if (addTaskListViewModel.addTaskListUiState.isAddingTaskList) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            Column(modifier = Modifier.padding(paddingValues)) {
                TitledTextField(
                    title = stringResource(resource = MR.strings.name),
                    value = taskListName,
                    onValueChange = {
                        taskListName = it
                        taskListNameInputError = false
                    },
                    placeholder = { Text(stringResource(resource = MR.strings.enter_task_list_name)) },
                    singleLine = true,
                    isError = taskListNameInputError,
                    errorMessage = stringResource(resource = MR.strings.field_not_empty),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                )
            }
        }
    )
}
