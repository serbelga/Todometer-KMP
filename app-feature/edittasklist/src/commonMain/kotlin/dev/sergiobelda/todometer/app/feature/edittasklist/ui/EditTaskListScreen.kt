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

package dev.sergiobelda.todometer.app.feature.edittasklist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import dev.sergiobelda.navigation.compose.extended.annotation.NavDestination
import dev.sergiobelda.todometer.app.common.designsystem.components.TodometerTitledTextField
import dev.sergiobelda.todometer.app.common.ui.components.SaveActionTopAppBar
import dev.sergiobelda.todometer.app.common.ui.loading.LoadingScreenDialog
import dev.sergiobelda.todometer.app.common.ui.values.TextFieldPadding
import dev.sergiobelda.todometer.common.resources.TodometerResources

@NavDestination(
    destinationId = "edittasklist",
    name = "EditTaskList"
)
@Composable
fun EditTaskListScreen(
    navigateBack: () -> Unit,
    viewModel: EditTaskListViewModel
) {
    when {
        viewModel.uiState.isLoading -> {
            LoadingScreenDialog(navigateBack)
        }

        !viewModel.uiState.isLoading -> {
            viewModel.uiState.taskList?.let { taskList ->
                var taskListName by rememberSaveable { mutableStateOf(taskList.name) }
                var taskListNameInputError by remember { mutableStateOf(false) }

                Scaffold(
                    topBar = {
                        SaveActionTopAppBar(
                            navigateBack = navigateBack,
                            title = TodometerResources.strings.edit_task_list,
                            isSaveButtonEnabled = !viewModel.uiState.isLoading,
                            onSaveButtonClick = {
                                if (taskListName.isBlank()) {
                                    taskListNameInputError = true
                                } else {
                                    viewModel.updateTaskList(taskListName)
                                    navigateBack()
                                }
                            }
                        )
                    },
                    content = { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            TodometerTitledTextField(
                                title = TodometerResources.strings.name,
                                value = taskListName,
                                onValueChange = {
                                    taskListName = it
                                    taskListNameInputError = false
                                },
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
                )
            }
        }
    }
}
