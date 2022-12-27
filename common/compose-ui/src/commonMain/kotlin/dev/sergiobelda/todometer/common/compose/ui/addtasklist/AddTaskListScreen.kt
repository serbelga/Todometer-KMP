package dev.sergiobelda.todometer.common.compose.ui.addtasklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.TitledTextField
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.compose.ui.resources.stringResource
import dev.sergiobelda.todometer.common.resources.MR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskListTopBar(
    navigateBack: () -> Unit,
    isSaveButtonEnabled: Boolean,
    onSaveButtonClick: () -> Unit,
    saveButtonTintColor: Color = Color.Unspecified
) {
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
        title = { Text(stringResource(MR.strings.add_task_list)) },
        actions = {
            IconButton(
                enabled = isSaveButtonEnabled,
                onClick = onSaveButtonClick
            ) {
                Icon(
                    Icons.Rounded.Check,
                    contentDescription = "Save",
                    tint = saveButtonTintColor
                )
            }
        }
    )
}

@Composable
fun AddTaskListContent(
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
        TitledTextField(
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
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        )
    }
}
