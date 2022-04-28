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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.tasklist.TaskListProgress
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.onSurfaceMediumEmphasis
import dev.sergiobelda.todometer.common.domain.model.Task
import dev.sergiobelda.todometer.ui.theme.ToDometerTheme

@Composable
fun ToDometerTopAppBar(
    onMenuClick: () -> Unit,
    onMoreClick: () -> Unit,
    taskListName: String?,
    tasks: List<Task>
) {
    Box {
        Column {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            Icons.Rounded.Menu,
                            contentDescription = "Menu",
                            tint = TodometerColors.onSurfaceMediumEmphasis
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onMoreClick) {
                        Icon(
                            Icons.Rounded.MoreVert,
                            contentDescription = "More",
                            tint = TodometerColors.onSurfaceMediumEmphasis
                        )
                    }
                },
                backgroundColor = TodometerColors.surface,
                elevation = 0.dp
            )
            TaskListProgress(taskListName, tasks)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            ToDometerTitle()
        }
    }
}

@Preview
@Composable
fun ToDometerTopAppBarPreview() {
    ToDometerTheme {
        ToDometerTopAppBar({}, {}, "", emptyList())
    }
}
