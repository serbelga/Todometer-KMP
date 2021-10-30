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

package com.sergiobelda.todometer.compose.ui.project

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import com.sergiobelda.todometer.compose.ui.theme.TodometerTypography
import com.sergiobelda.todometer.compose.ui.theme.primarySelected

@Composable
fun ProjectListItem(
    project: Project,
    selected: Boolean,
    onItemClick: (String) -> Unit
) {
    val background = if (selected) {
        Modifier.background(TodometerColors.primarySelected)
    } else {
        Modifier
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(56.dp).clickable(onClick = { onItemClick(project.id) })
            .then(background)
    ) {
        val selectedColor =
            if (selected) TodometerColors.primary else TodometerColors.onSurface.copy(alpha = ContentAlpha.medium)
        Text(
            text = project.name,
            color = selectedColor,
            style = TodometerTypography.subtitle2,
            modifier = Modifier.weight(1f).padding(start = 16.dp, end = 16.dp)
        )
    }
}
