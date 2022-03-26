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

package dev.sergiobelda.todometer.ui.edittasklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.compose.ui.components.TitledTextField
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.onSurfaceMediumEmphasis
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.model.TaskList
import org.koin.androidx.compose.getViewModel

@Composable
fun EditTaskListScreen(
    navigateUp: () -> Unit,
    editTaskListViewModel: EditTaskListViewModel = getViewModel()
) {
    val taskListResultState = editTaskListViewModel.taskListSelected.collectAsState()
    taskListResultState.value.doIfError {
        navigateUp()
    }.doIfSuccess { taskList ->
        var taskListName by rememberSaveable { mutableStateOf(taskList.name) }
        var taskListNameInputError by remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = TodometerColors.surface,
                    contentColor = contentColorFor(TodometerColors.surface),
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                Icons.Rounded.ArrowBack,
                                contentDescription = "Back",
                                tint = TodometerColors.onSurfaceMediumEmphasis
                            )
                        }
                    },
                    title = { Text(stringResource(id = R.string.edit_task_list)) },
                    actions = {
                        IconButton(
                            onClick = {
                                if (taskListName.isBlank()) {
                                    taskListNameInputError = true
                                } else {
                                    editTaskListViewModel.updateTaskList(
                                        TaskList(
                                            id = taskList.id,
                                            name = taskListName,
                                            description = taskList.description,
                                            sync = false
                                        )
                                    )
                                    navigateUp()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Rounded.Check,
                                contentDescription = "Save",
                                tint = TodometerColors.primary
                            )
                        }
                    },
                )
            },
            content = {
                Column(modifier = Modifier.padding(top = 24.dp)) {
                    TitledTextField(
                        title = stringResource(R.string.name),
                        value = taskListName,
                        onValueChange = {
                            taskListName = it
                            taskListNameInputError = false
                        },
                        placeholder = { Text(stringResource(id = R.string.enter_task_list_name)) },
                        singleLine = true,
                        isError = taskListNameInputError,
                        errorMessage = stringResource(R.string.field_not_empty),
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
}
