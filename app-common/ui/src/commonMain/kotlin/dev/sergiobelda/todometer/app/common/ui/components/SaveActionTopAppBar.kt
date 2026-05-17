/*
 * Copyright 2023 Sergio Belda
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

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Check
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.NavigateBefore
import dev.sergiobelda.todometer.common.resources.Res
import dev.sergiobelda.todometer.common.resources.back
import dev.sergiobelda.todometer.common.resources.save
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveActionTopAppBar(
    navigateBack: () -> Unit,
    title: String,
    onSaveButtonClick: () -> Unit,
    isSaveButtonEnabled: Boolean = true,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    Images.Icons.NavigateBefore,
                    contentDescription = stringResource(Res.string.back),
                )
            }
        },
        title = { Text(title) },
        actions = {
            TextButton(
                enabled = isSaveButtonEnabled,
                onClick = onSaveButtonClick,
            ) {
                Icon(
                    Images.Icons.Check,
                    contentDescription = stringResource(Res.string.save),
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = stringResource(Res.string.save),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
    )
}
