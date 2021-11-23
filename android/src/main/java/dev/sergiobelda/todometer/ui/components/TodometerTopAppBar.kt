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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ProjectProgress
import dev.sergiobelda.todometer.common.model.Project
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.ui.theme.ToDometerTheme

@Composable
fun ToDometerTopAppBar(project: Project?, tasks: List<Task>) {
    Surface(
        modifier = Modifier.wrapContentHeight().fillMaxWidth()
    ) {
        Column {
            Box(
                modifier = Modifier.height(56.dp).fillMaxWidth()
            ) {
                ToDometerTitle(modifier = Modifier.align(Alignment.Center))
                /*
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(Icons.Outlined.AccountCircle, contentDescription = "Account")
                    }
                }
                */
            }
            project?.let { ProjectProgress(it, tasks) }
        }
    }
}

@Preview
@Composable
fun TodometerTopAppBarPreview() {
    ToDometerTheme {
        ToDometerTopAppBar(null, emptyList())
    }
}
