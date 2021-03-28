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

package com.sergiobelda.todometer.compose.ui.task

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.model.TaskState
import com.sergiobelda.todometer.compose.ui.theme.MaterialColors
import com.sergiobelda.todometer.compose.ui.theme.MaterialTypography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskItem(
    task: Task,
    onDoingClick: (Long) -> Unit,
    onDoneClick: (Long) -> Unit,
    onClick: (Long) -> Unit,
    onLongClick: (Long) -> Unit,
    emptyDescriptionString: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.combinedClickable(
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
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Icon(Icons.Rounded.DateRange, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                }
                when (task.state) {
                    TaskState.DOING -> {
                        Text(task.title)
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { onDoneClick(task.id) }
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
                            onClick = { onDoingClick(task.id) }
                        ) {
                            Icon(Icons.Filled.Refresh, contentDescription = "Doing", tint = MaterialColors.secondary)
                        }
                    }
                }
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                if (!task.description.isNullOrBlank()) {
                    Text(
                        task.description ?: "",
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        style = MaterialTheme.typography.caption,
                        maxLines = 3
                    )
                } else {
                    Text(
                        text = emptyDescriptionString, // stringResource(id = R.string.no_description),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        style = MaterialTypography.caption,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}
