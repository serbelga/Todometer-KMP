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
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import com.sergiobelda.todometer.compose.ui.theme.TodometerTypography

@Composable
fun TodometerTextField(
    title: String = "",
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    maxLines: Int = 10,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        errorBorderColor = Color.Transparent
    )
) {
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            top = 8.dp,
            bottom = 8.dp
        )
    ) {
        Text(
            title,
            color = TodometerColors.primary,
            style = TodometerTypography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            placeholder = placeholder,
            modifier = modifier.padding(top = 4.dp).fillMaxWidth(),
            keyboardOptions = keyboardOptions,
            isError = isError,
            singleLine = singleLine,
            maxLines = maxLines,
            colors = colors
        )
        if (isError) {
            Text(
                errorMessage,
                color = TodometerColors.error,
                style = TodometerTypography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
