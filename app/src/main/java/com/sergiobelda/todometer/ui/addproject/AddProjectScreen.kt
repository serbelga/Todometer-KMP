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

package com.sergiobelda.todometer.ui.addproject

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.R
import com.sergiobelda.todometer.model.Project
import com.sergiobelda.todometer.viewmodel.MainViewModel

@Composable
fun AddProjectScreen(
    mainViewModel: MainViewModel,
    upPress: () -> Unit
) {
    var projectName by savedInstanceState { "" }
    var projectDescription by savedInstanceState { "" }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = contentColorFor(MaterialTheme.colors.surface),
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = upPress) {
                        Icon(Icons.Rounded.ArrowBack)
                    }
                },
                title = { Text(stringResource(id = R.string.add_project)) },
                actions = {
                    Button(
                        onClick = {
                            mainViewModel.insertProject(
                                Project(
                                    name = projectName,
                                    description = projectDescription
                                )
                            )
                            upPress()
                        }
                    ) {
                        Text(stringResource(id = R.string.save))
                    }
                }
            )
        },
        bodyContent = {
            Column {
                OutlinedTextField(
                    value = projectName,
                    onValueChange = { projectName = it },
                    label = { Text(stringResource(id = R.string.name)) }
                )
                OutlinedTextField(
                    value = projectDescription,
                    onValueChange = { projectDescription = it },
                    label = { Text(stringResource(id = R.string.description)) }
                )
            }
        }
    )
}
