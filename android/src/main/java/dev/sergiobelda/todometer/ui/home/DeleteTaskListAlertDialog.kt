package dev.sergiobelda.todometer.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.sergiobelda.todometer.R

@Composable
fun DeleteTaskListAlertDialog(onDismissRequest: () -> Unit, onDeleteTaskListClick: () -> Unit) {
    AlertDialog(
        icon = {
            Icon(
                Icons.Rounded.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(stringResource(id = R.string.delete_task_list))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(id = R.string.delete_task_list_question))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteTaskListClick()
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
