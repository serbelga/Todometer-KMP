/*
 * Copyright 2024 Sergio Belda
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
