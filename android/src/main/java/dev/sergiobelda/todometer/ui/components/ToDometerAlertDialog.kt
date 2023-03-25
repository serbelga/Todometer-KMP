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

package dev.sergiobelda.todometer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

private const val DialogTonalElevation = 6

// TODO: Remove
@Composable
internal fun ToDometerAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    body: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor)
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        AlertDialogContent(
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    dismissButton?.invoke()
                    confirmButton()
                }
            },
            modifier = modifier,
            title = title,
            body = body,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    }
}

// TODO: Remove
@Composable
internal fun AlertDialogContent(
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)?,
    body: @Composable (() -> Unit)?,
    shape: Shape,
    backgroundColor: Color,
    contentColor: Color
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        tonalElevation = DialogTonalElevation.dp
    ) {
        Column {
            title?.let {
                val textStyle = MaterialTheme.typography.headlineSmall
                ProvideTextStyle(textStyle) { title() }
            }
            body?.let {
                val textStyle = MaterialTheme.typography.bodyMedium
                ProvideTextStyle(textStyle) { body() }
            }
            buttons()
        }
    }
}
