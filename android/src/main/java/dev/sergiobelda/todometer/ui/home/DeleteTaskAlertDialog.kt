package dev.sergiobelda.todometer.ui.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.sergiobelda.todometer.R

@Composable
fun DeleteTaskAlertDialog(onDismissRequest: () -> Unit, onDeleteTaskClick: () -> Unit) {
    AlertDialog(
        title = {
            Text(stringResource(id = R.string.delete_task))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(id = R.string.delete_task_question))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteTaskClick()
                    onDismissRequest()
                }
            ) {
                Text(stringResource(android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}
