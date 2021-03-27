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

package com.sergiobelda.todometer.ui.project

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.sergiobelda.todometer.compose.ui.theme.MaterialColors
import com.sergiobelda.todometer.viewmodel.MainViewModel

@Composable
fun ProjectDetailScreen(
    projectId: Int,
    mainViewModel: MainViewModel,
    navigateUp: () -> Unit
) {
    val projectState = mainViewModel.getProject(projectId).observeAsState()
    projectState.value?.let { project ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(project.name)
                    },
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                        }
                    },
                    backgroundColor = MaterialColors.surface,
                    contentColor = contentColorFor(backgroundColor = MaterialColors.surface)
                )
            },
            content = {
            }
        )
    }
}
