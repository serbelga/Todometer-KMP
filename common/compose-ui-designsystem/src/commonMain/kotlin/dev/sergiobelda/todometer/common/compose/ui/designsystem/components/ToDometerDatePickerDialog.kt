package dev.sergiobelda.todometer.common.compose.ui.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDometerDatePickerDialog(
    state: DatePickerState,
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    tonalElevation: Dp = ToDometerDatePickerDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Box(
            modifier = modifier
                .sizeIn(minWidth = DialogMinWidth, maxWidth = DialogMaxWidth),
            propagateMinConstraints = true
        ) {
            Surface(
                modifier = Modifier
                    .requiredWidth(ContainerWidth)
                    .heightIn(max = ContainerHeight),
                tonalElevation = tonalElevation,
                shape = shape
            ) {
                Column {
                    DatePicker(state = state)
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp, end = 24.dp)
                    ) {
                        dismissButton?.invoke()
                        Spacer(modifier = Modifier.width(12.dp))
                        confirmButton()
                    }
                }
            }
        }
    }
}

object ToDometerDatePickerDefaults {

    val TonalElevation = 6.0.dp
}

private val DialogMinWidth = 280.dp
private val DialogMaxWidth = 560.dp
private val ContainerHeight = 572.0.dp
private val ContainerWidth = 360.0.dp
