/*
 * Copyright 2021 Sergio Belda
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

package dev.sergiobelda.todometer.wearapp.wearos.ui.deletetasklist

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults.secondaryButtonColors
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Alert
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Check
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Close
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Delete
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
internal fun DeleteTaskListAlertDialog(
    onDeleteTaskList: () -> Unit,
    onCancel: () -> Unit,
) {
    Alert(
        icon = {
            Icon(
                Images.Icons.Delete,
                TodometerResources.strings.deleteTaskList,
            )
        },
        title = {},
        content = { Text(TodometerResources.strings.deleteTaskListQuestion) },
        positiveButton = {
            Button(
                onClick = onDeleteTaskList,
            ) {
                Icon(Images.Icons.Check, null)
            }
        },
        negativeButton = {
            Button(colors = secondaryButtonColors(), onClick = onCancel) {
                Icon(Images.Icons.Close, null)
            }
        },
    )
}
