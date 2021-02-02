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

package com.sergiobelda.todometer.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.R
import com.sergiobelda.todometer.model.Task
import com.sergiobelda.todometer.model.TaskState
import com.sergiobelda.todometer.ui.theme.MaterialColors
import com.sergiobelda.todometer.ui.theme.MaterialTypography
import com.sergiobelda.todometer.ui.theme.outline

@Composable
fun TaskItem(
    task: Task,
    updateState: (Int, TaskState) -> Unit,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        shape = MaterialTheme.shapes.large,
        elevation = 0.dp,
        border = BorderStroke(1.dp, MaterialColors.outline)
    ) {
        Column(
            modifier = Modifier.clickable(
                onClick = {
                    onClick(task.id)
                },
                onLongClick = {
                    onLongClick(task.id)
                }
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                    Icon(Icons.Rounded.DateRange, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                }
                when (task.state) {
                    TaskState.DOING -> {
                        Text(task.title)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { updateState(task.id, TaskState.DONE) }
                        ) {
                            Icon(Icons.Rounded.Check, contentDescription = "Done", tint = MaterialColors.secondary)
                        }
                    }
                    TaskState.DONE -> {
                        Text(
                            task.title,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { updateState(task.id, TaskState.DOING) }
                        ) {
                            Icon(Icons.Filled.Refresh, contentDescription = "Doing", tint = MaterialColors.secondary)
                        }
                    }
                }
            }
            Providers(AmbientContentAlpha provides ContentAlpha.medium) {
                if (task.description.isNotBlank()) {
                    Text(
                        task.description,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        style = MaterialTheme.typography.caption,
                        maxLines = 3
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.no_description),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        style = MaterialTypography.caption,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    // TaskItem(task = task2, updateState = { _, _ -> }, onClick = {})
}
