package dev.sergiobelda.todometer.app.common.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                stringResource(MR.strings.select_time),
                style = MaterialTheme.typography.labelLarge
            )
        },
        text = {
            content()
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(stringResource(MR.strings.cancel))
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(stringResource(MR.strings.ok))
            }
        }
    )
}
