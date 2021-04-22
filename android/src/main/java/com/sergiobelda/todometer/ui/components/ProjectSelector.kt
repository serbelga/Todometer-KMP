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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.compose.ui.components.HorizontalDivider
import java.util.Locale

@Composable
fun ProjectSelector(
    projectList: List<Project>,
    selectedProject: Project?,
    onProjectSelected: (project: Project) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.project).toUpperCase(Locale.ROOT),
            style = MaterialTheme.typography.overline,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
        LazyColumn(
            modifier = Modifier.height(140.dp)
        ) {
            items(projectList) { project ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (project == selectedProject),
                            onClick = { onProjectSelected(project) }
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (project == selectedProject),
                        onClick = { onProjectSelected(project) }
                    )
                    Text(
                        text = project.name,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                HorizontalDivider()
            }
        }
    }
}
