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

package dev.sergiobelda.todometer.ui.edittask

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.compose.ui.components.TitledTextField
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.data.doIfError
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.ui.components.TodometerTagSelector
import org.koin.androidx.compose.getViewModel

@Composable
fun EditTaskScreen(
    taskId: String,
    navigateUp: () -> Unit,
    editTaskViewModel: EditTaskViewModel = getViewModel()
) {
    editTaskViewModel.getTask(taskId)
    val taskResultState = editTaskViewModel.task.collectAsState()
    taskResultState.value.doIfError {
        navigateUp()
    }.doIfSuccess { task ->
        var taskTitle by rememberSaveable { mutableStateOf(task.title) }
        var taskTitleInputError: Boolean by remember { mutableStateOf(false) }
        var taskDescription by rememberSaveable { mutableStateOf(task.description) }
        var selectedTag by remember { mutableStateOf(task.tag) }
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = TodometerColors.surface,
                    contentColor = contentColorFor(TodometerColors.surface),
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                if (taskTitle.isBlank()) {
                                    taskTitleInputError = true
                                } else {
                                    editTaskViewModel.updateTask(
                                        Task(
                                            id = task.id,
                                            title = taskTitle,
                                            description = taskDescription,
                                            state = task.state,
                                            projectId = task.projectId,
                                            tag = selectedTag,
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
                    title = { Text(stringResource(id = R.string.edit_task)) }
                )
            },
            content = {
                Column {
                    TitledTextField(
                        title = stringResource(id = R.string.name),
                        value = taskTitle,
                        onValueChange = {
                            taskTitle = it
                            taskTitleInputError = false
                        },
                        placeholder = { Text(stringResource(id = R.string.enter_task_name)) },
                        isError = taskTitleInputError,
                        errorMessage = stringResource(id = R.string.field_not_empty),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        )
                    )
                    TodometerTagSelector(
                        selectedTag
                    ) { tag ->
                        selectedTag = tag
                    }
                    TitledTextField(
                        title = stringResource(id = R.string.description),
                        value = taskDescription ?: "",
                        onValueChange = { taskDescription = it },
                        placeholder = { Text(stringResource(id = R.string.enter_description)) },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        )
                    )
                }
            }
        )
    }
}