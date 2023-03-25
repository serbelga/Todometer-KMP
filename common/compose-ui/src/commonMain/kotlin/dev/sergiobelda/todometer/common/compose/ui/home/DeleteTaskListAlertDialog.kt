package dev.sergiobelda.todometer.common.compose.ui.home

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.ToDometerAlertDialog
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
fun DeleteTaskListAlertDialog(onDismissRequest: () -> Unit, onDeleteTaskListClick: () -> Unit) {
    ToDometerAlertDialog(
        title = {
            Text(stringResource(resource = MR.strings.delete_task_list))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(resource = MR.strings.delete_task_list_question))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteTaskListClick()
                    onDismissRequest()
                }
            ) {
                Text(stringResource(resource = MR.strings.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(resource = MR.strings.cancel))
            }
        }
    )
}
