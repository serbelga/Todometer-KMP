/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.common.compose.ui.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerChip
import dev.sergiobelda.todometer.common.compose.ui.theme.ToDometerTheme

@Composable
fun TaskChecklistItemsChip(checklistItemsDone: Long, totalChecklistItems: Long) {
    val completedChipTint =
        if (checklistItemsDone == totalChecklistItems) ToDometerTheme.toDometerColors.check else ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
    val completedChipOutline =
        if (checklistItemsDone == totalChecklistItems) ToDometerTheme.toDometerColors.check else MaterialTheme.colorScheme.outline

    ToDometerChip(
        borderStroke = BorderStroke(1.dp, completedChipOutline),
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Icon(
            Icons.Outlined.CheckBox,
            contentDescription = null,
            modifier = Modifier.size(16.dp).padding(end = 4.dp),
            tint = completedChipTint
        )
        Text(
            "$checklistItemsDone/$totalChecklistItems",
            style = MaterialTheme.typography.labelLarge,
            color = completedChipTint
        )
    }
}
