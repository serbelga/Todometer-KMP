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

package dev.sergiobelda.todometer.common.compose.ui.components.tasklist

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListItem(
    text: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val colors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
        unselectedTextColor = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
    )
    NavigationDrawerItem(
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1
            )
        },
        onClick = onItemClick,
        selected = isSelected,
        colors = colors
    )
}
