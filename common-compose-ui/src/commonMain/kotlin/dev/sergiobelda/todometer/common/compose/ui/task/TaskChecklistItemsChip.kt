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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerChip
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerTypography
import dev.sergiobelda.todometer.common.compose.ui.theme.green
import dev.sergiobelda.todometer.common.compose.ui.theme.onSurfaceMediumEmphasis
import dev.sergiobelda.todometer.common.compose.ui.theme.outline
import dev.sergiobelda.todometer.common.compose.ui.theme.teal

@Composable
fun TaskChecklistItemsChip(checklistItemsDone: Long, totalChecklistItems: Long) {
    val completedChipTint =
        if (checklistItemsDone == totalChecklistItems) TodometerColors.teal else TodometerColors.onSurfaceMediumEmphasis
    val completedChipOutline =
        if (checklistItemsDone == totalChecklistItems) TodometerColors.teal else TodometerColors.outline

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
            style = TodometerTypography.caption,
            color = completedChipTint
        )
    }
}
