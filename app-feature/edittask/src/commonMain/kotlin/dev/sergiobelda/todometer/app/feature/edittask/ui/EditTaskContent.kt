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

package dev.sergiobelda.todometer.app.feature.edittask.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import dev.sergiobelda.fonament.ui.FonamentContent
import dev.sergiobelda.fonament.ui.FonamentUI
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgument
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgumentType
import dev.sergiobelda.navigation.compose.extended.annotation.NavDestination
import dev.sergiobelda.todometer.app.common.designsystem.components.TodometerDivider
import dev.sergiobelda.todometer.app.common.designsystem.components.TodometerTitledTextField
import dev.sergiobelda.todometer.app.common.ui.components.DatePickerDialog
import dev.sergiobelda.todometer.app.common.ui.components.DateTimeSelector
import dev.sergiobelda.todometer.app.common.ui.components.SaveActionTopAppBar
import dev.sergiobelda.todometer.app.common.ui.components.TagSelector
import dev.sergiobelda.todometer.app.common.ui.components.TimePickerDialog
import dev.sergiobelda.todometer.app.common.ui.extensions.addStyledOptionalSuffix
import dev.sergiobelda.todometer.app.common.ui.loading.LoadingScreenDialog
import dev.sergiobelda.todometer.app.common.ui.values.SectionPadding
import dev.sergiobelda.todometer.app.common.ui.values.TextFieldPadding
import dev.sergiobelda.todometer.app.feature.edittask.navigation.EditTaskNavigationEvent
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.resources.TodometerResources

data object EditTaskScreen : FonamentUI<EditTaskUIState>() {
    override val content: FonamentContent<EditTaskUIState, *> = EditTaskContent
}

data object EditTaskContent : FonamentContent<EditTaskUIState, EditTaskContentState>() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun createContentState(
        uiState: EditTaskUIState,
    ): EditTaskContentState = rememberEditTaskContentState(
        task = uiState.task,
    )

    @NavDestination(
        destinationId = "edittask",
        name = "EditTask",
        arguments = [
            NavArgument("taskId", NavArgumentType.String),
        ],
    )
    @Composable
    override fun Content(
        uiState: EditTaskUIState,
        contentState: EditTaskContentState,
        modifier: Modifier,
    ) {
        when {
            uiState.isLoading -> {
                LoadingScreenDialog(
                    navigateBack = {
                        onEvent(
                            EditTaskNavigationEvent.NavigateBack,
                        )
                    },
                )
            }

            !uiState.isLoading -> {
                EditTaskScaffold(
                    contentState = contentState,
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun EditTaskScaffold(
        contentState: EditTaskContentState,
    ) {
        Scaffold(
            topBar = {
                EditTaskTopBar(
                    isSaveButtonEnabled = contentState.isSaveButtonEnabled,
                    onSaveButtonClick = {
                        onEvent(
                            EditTaskEvent.UpdateTask(
                                title = contentState.title,
                                tag = contentState.tag,
                                description = contentState.description,
                                dueDate = contentState.dueDate,
                            ),
                        )
                        onEvent(EditTaskNavigationEvent.NavigateBack)
                    },
                )
            },
            content = { paddingValues ->
                EditTaskContent(
                    title = contentState.title,
                    description = contentState.description,
                    dueDate = contentState.dueDate,
                    tag = contentState.tag,
                    modifier = Modifier.padding(paddingValues),
                )
            },
        )
        if (contentState.datePickerState != null && contentState.datePickerDialogVisible) {
            DatePickerDialog(
                onDismissRequest = { onEvent(EditTaskEvent.DismissDatePickerDialog) },
                onConfirm = { onEvent(EditTaskEvent.ConfirmDatePickerDialog) },
            ) {
                DatePicker(state = contentState.datePickerState)
            }
        }
        if (contentState.timePickerState != null && contentState.timePickerDialogVisible) {
            TimePickerDialog(
                onDismissRequest = { onEvent(EditTaskEvent.DismissTimePickerDialog) },
                onConfirm = { onEvent(EditTaskEvent.ConfirmTimePickerDialog) },
            ) {
                TimePicker(state = contentState.timePickerState)
            }
        }
    }

    @Composable
    private fun EditTaskTopBar(
        isSaveButtonEnabled: Boolean,
        onSaveButtonClick: () -> Unit,
    ) {
        SaveActionTopAppBar(
            navigateBack = { onEvent(EditTaskNavigationEvent.NavigateBack) },
            isSaveButtonEnabled = isSaveButtonEnabled,
            title = TodometerResources.strings.editTask,
            onSaveButtonClick = onSaveButtonClick,
        )
    }

    @Composable
    private fun EditTaskContent(
        title: String,
        description: String,
        dueDate: Long?,
        tag: Tag,
        modifier: Modifier,
    ) {
        Column(modifier = modifier) {
            TodometerTitledTextField(
                title = TodometerResources.strings.name,
                value = title,
                onValueChange = { onEvent(EditTaskEvent.TitleValueChange(it)) },
                placeholder = { Text(TodometerResources.strings.enterTaskName) },
                errorMessage = TodometerResources.strings.fieldNotEmpty,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.padding(TextFieldPadding),
            )
            Text(
                text = TodometerResources.strings.chooseTag.addStyledOptionalSuffix(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = SectionPadding),
            )
            TagSelector(
                tag = tag,
                onTagSelected = { onEvent(EditTaskEvent.SelectTag(it)) },
            )
            Text(
                text = TodometerResources.strings.dateTime.addStyledOptionalSuffix(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = SectionPadding),
            )
            DateTimeSelector(
                dateMillis = dueDate,
                onEnterDateTimeClick = { onEvent(EditTaskEvent.ShowDatePickerDialog) },
                onDateClick = { onEvent(EditTaskEvent.ShowDatePickerDialog) },
                onTimeClick = { onEvent(EditTaskEvent.ShowTimePickerDialog) },
                onClearDateTimeClick = { onEvent(EditTaskEvent.ClearDateTime) },
            )
            TodometerTitledTextField(
                title = TodometerResources.strings.description.addStyledOptionalSuffix(),
                value = description,
                onValueChange = { onEvent(EditTaskEvent.DescriptionValueChange(it)) },
                placeholder = { Text(TodometerResources.strings.enterDescription) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.padding(TextFieldPadding),
                maxLines = 4,
            )
            TodometerDivider()
        }
    }
}
