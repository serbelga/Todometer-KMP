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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import dev.sergiobelda.todometer.common.compose.ui.components.DatePickerDialog
import dev.sergiobelda.todometer.common.compose.ui.components.DateTimeSelector
import dev.sergiobelda.todometer.common.compose.ui.components.SaveActionTopAppBar
import dev.sergiobelda.todometer.common.compose.ui.components.TagSelector
import dev.sergiobelda.todometer.common.compose.ui.components.TimePickerDialog
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.TodometerDivider
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.TodometerTitledTextField
import dev.sergiobelda.todometer.common.compose.ui.extensions.addStyledOptionalSuffix
import dev.sergiobelda.todometer.common.compose.ui.extensions.selectedTimeMillis
import dev.sergiobelda.todometer.common.compose.ui.loading.LoadingScreenDialog
import dev.sergiobelda.todometer.common.compose.ui.values.SectionPadding
import dev.sergiobelda.todometer.common.compose.ui.values.TextFieldPadding
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource
import dev.sergiobelda.todometer.common.ui.extensions.localTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    navigateBack: () -> Unit,
    updateTask: (taskTitle: String, selectedTag: Tag, taskDescription: String, taskDueDate: Long?) -> Unit,
    editTaskUiState: EditTaskUiState
) {
    when {
        editTaskUiState.isLoading -> {
            LoadingScreenDialog(navigateBack)
        }

        !editTaskUiState.isLoading && editTaskUiState.task != null -> {
            var taskTitle by rememberSaveable { mutableStateOf(editTaskUiState.task.title) }
            var taskTitleInputError: Boolean by remember { mutableStateOf(false) }
            var taskDescription by rememberSaveable {
                mutableStateOf(
                    editTaskUiState.task.description ?: ""
                )
            }
            var selectedTag by rememberSaveable { mutableStateOf(editTaskUiState.task.tag) }
            var taskDueDate: Long? by rememberSaveable { mutableStateOf(editTaskUiState.task.dueDate) }

            var datePickerDialogState by remember { mutableStateOf(false) }
            val datePickerState =
                rememberDatePickerState(initialSelectedDateMillis = editTaskUiState.task.dueDate)

            var timePickerDialogState by remember { mutableStateOf(false) }
            val timePickerState = rememberTimePickerState(
                initialHour = editTaskUiState.task.dueDate?.localTime()?.hour ?: 0,
                initialMinute = editTaskUiState.task.dueDate?.localTime()?.minute ?: 0
            )

            Scaffold(
                topBar = {
                    SaveActionTopAppBar(
                        navigateBack = navigateBack,
                        title = stringResource(MR.strings.edit_task),
                        isSaveButtonEnabled = !editTaskUiState.isLoading,
                        onSaveButtonClick = {
                            if (taskTitle.isBlank()) {
                                taskTitleInputError = true
                            } else {
                                updateTask(taskTitle, selectedTag, taskDescription, taskDueDate)
                                navigateBack()
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        TodometerTitledTextField(
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
                            modifier = Modifier.padding(TextFieldPadding)
                        )
                        Text(
                            text = stringResource(MR.strings.choose_tag).addStyledOptionalSuffix(),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(horizontal = SectionPadding)
                        )
                        TagSelector(selectedTag) { tag ->
                            selectedTag = tag
                        }
                        Text(
                            text = stringResource(MR.strings.date_time).addStyledOptionalSuffix(),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(horizontal = SectionPadding)
                        )
                        DateTimeSelector(
                            taskDueDate,
                            onEnterDateTimeClick = { datePickerDialogState = true },
                            onDateClick = { datePickerDialogState = true },
                            onTimeClick = { timePickerDialogState = true },
                            onClearDateTimeClick = { taskDueDate = null }
                        )
                        TodometerTitledTextField(
                            title = stringResource(MR.strings.description).addStyledOptionalSuffix(),
                            value = taskDescription,
                            onValueChange = { taskDescription = it },
                            placeholder = { Text(stringResource(MR.strings.enter_description)) },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.padding(TextFieldPadding),
                            maxLines = 4
                        )
                        TodometerDivider()
                    }
                }
            )
            if (datePickerDialogState) {
                DatePickerDialog(
                    onDismissRequest = { datePickerDialogState = false },
                    onConfirm = {
                        datePickerDialogState = false
                        taskDueDate =
                            datePickerState.selectedDateMillis?.plus(timePickerState.selectedTimeMillis)
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            if (timePickerDialogState) {
                TimePickerDialog(
                    onDismissRequest = { timePickerDialogState = false },
                    onConfirm = {
                        timePickerDialogState = false
                        taskDueDate =
                            datePickerState.selectedDateMillis?.plus(timePickerState.selectedTimeMillis)
                    }
                ) {
                    TimePicker(state = timePickerState)
                }
            }
        }
    }
}
