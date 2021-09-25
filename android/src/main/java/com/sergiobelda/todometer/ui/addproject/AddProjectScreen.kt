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

package com.sergiobelda.todometer.ui.addproject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.data.doIfSuccess
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import com.sergiobelda.todometer.ui.components.TodometerTextField
import org.koin.androidx.compose.getViewModel

@Composable
fun AddProjectScreen(
    navigateUp: () -> Unit,
    addProjectViewModel: AddProjectViewModel = getViewModel()
) {
    var projectName by rememberSaveable { mutableStateOf("") }
    var projectNameInputError by remember { mutableStateOf(false) }
    val result = addProjectViewModel.result.observeAsState()
    result.value?.doIfSuccess {
        navigateUp()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = TodometerColors.surface,
                contentColor = contentColorFor(TodometerColors.surface),
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                title = { Text(stringResource(id = R.string.add_project)) },
                actions = {
                    IconButton(
                        onClick = {
                            if (projectName.isNullOrBlank()) {
                                projectNameInputError = true
                            } else {
                                addProjectViewModel.insertProject(projectName)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = "Save",
                            tint = TodometerColors.primary
                        )
                    }
                },
            )
        },
        content = {
            Column(modifier = Modifier.padding(top = 24.dp)) {
                TodometerTextField(
                    title = stringResource(R.string.name),
                    value = projectName,
                    onValueChange = {
                        projectName = it
                        projectNameInputError = false
                    },
                    placeholder = { Text(stringResource(id = R.string.enter_project_name)) },
                    singleLine = true,
                    isError = projectNameInputError,
                    errorMessage = stringResource(R.string.field_not_empty),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done
                    )
                )
            }
        }
    )
}
