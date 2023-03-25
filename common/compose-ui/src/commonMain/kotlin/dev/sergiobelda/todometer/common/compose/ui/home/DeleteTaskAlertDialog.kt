package dev.sergiobelda.todometer.common.compose.ui.home

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.ToDometerAlertDialog
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
fun DeleteTaskAlertDialog(onDismissRequest: () -> Unit, onDeleteTaskClick: () -> Unit) {
    ToDometerAlertDialog(
        title = {
            Text(stringResource(resource = MR.strings.delete_task))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(resource = MR.strings.delete_task_question))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteTaskClick()
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
