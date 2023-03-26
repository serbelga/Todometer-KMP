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

package dev.sergiobelda.todometer.common.compose.ui.designsystem.components

import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun ToDometerAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier,
    dismissButton: @Composable (() -> Unit)?,
    title: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit)?
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier.widthIn(
            min = ToDometerAlertDialogMinWidth,
            max = ToDometerAlertDialogMaxWidth
        ),
        dismissButton = dismissButton,
        title = title,
        text = text,
        backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
            ToDometerAlertDialogElevation
        ),
        contentColor = MaterialTheme.colorScheme.onSurface,
        shape = ToDometerAlertDialogShape,
    )
}

private val ToDometerAlertDialogMinWidth: Dp = 360.dp
private val ToDometerAlertDialogMaxWidth: Dp = 480.dp
private val ToDometerAlertDialogElevation: Dp = 6.dp
private val ToDometerAlertDialogShape: RoundedCornerShape = RoundedCornerShape(28.dp)
