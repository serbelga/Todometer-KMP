package ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeleteTaskAlertDialog(
    onDismissRequest: () -> Unit,
    onDeleteTask: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Delete task", modifier = Modifier.padding(start = 16.dp))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(text = "Do you want to delete this task?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteTask()
                    onDismissRequest()
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        modifier = Modifier.requiredWidth(480.dp)
    )
}
