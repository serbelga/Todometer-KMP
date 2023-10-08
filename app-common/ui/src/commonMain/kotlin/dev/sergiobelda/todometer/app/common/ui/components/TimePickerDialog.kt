package dev.sergiobelda.todometer.app.common.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.resources.TodometerResources

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
                TodometerResources.strings.select_time,
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
                Text(TodometerResources.strings.cancel)
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(TodometerResources.strings.ok)
            }
        }
    )
}
