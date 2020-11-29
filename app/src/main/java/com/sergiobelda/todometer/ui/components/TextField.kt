/*
 * Copyright 2020 Sergio Belda
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

package com.sergiobelda.todometer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.R
import com.sergiobelda.todometer.ui.theme.MaterialColors
import com.sergiobelda.todometer.ui.theme.MaterialTypography

@Composable
fun TextField(
    value: String,
    onValueChanged: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    isErrorValue: Boolean = false,
    error: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onImeActionPerformed: (ImeAction, SoftwareKeyboardController?) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp
        )
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChanged,
            label = label,
            modifier = modifier,
            keyboardOptions = keyboardOptions,
            isErrorValue = isErrorValue,
            onImeActionPerformed = onImeActionPerformed
        )
        if (isErrorValue) {
            Text(
                stringResource(id = R.string.field_not_empty),
                color = MaterialColors.error,
                style = MaterialTypography.caption
            )
        }
    }
}
