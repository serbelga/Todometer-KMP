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

package ui.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import com.sergiobelda.todometer.compose.ui.task.TaskItem
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import koin
import kotlinx.coroutines.launch
import ui.components.ToDometerTopAppBar

@Composable
fun HomeScreen(
    addTask: () -> Unit
) {
    val setTaskDoingUseCase = koin.get<SetTaskDoingUseCase>()
    val setTaskDoneUseCase = koin.get<SetTaskDoneUseCase>()
    val getProjectSelectedUseCase = koin.get<GetProjectSelectedUseCase>()
    val coroutineScope = rememberCoroutineScope()
    val setTaskDoing: (Long) -> Unit = {
        coroutineScope.launch {
            setTaskDoingUseCase(it)
        }
    }
    val setTaskDone: (Long) -> Unit = {
        coroutineScope.launch {
            setTaskDoneUseCase(it)
        }
    }
    val project: Project? by getProjectSelectedUseCase().collectAsState(null)
    // val tasks: List<Task> by getTasksUseCase().collectAsState(emptyList())
    Scaffold(
        topBar = {
            ToDometerTopAppBar(project)
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = TodometerColors.surface,
                contentColor = contentColorFor(TodometerColors.surface),
                cutoutShape = CircleShape
            ) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Rounded.MoreVert, contentDescription = "More")
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = addTask
            ) {
                Icon(Icons.Rounded.Add, contentDescription = "Add task")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
        project?.let {
            LazyColumn {
                items(it.tasks) {
                    TaskItem(
                        task = it,
                        onDoingClick = setTaskDoing,
                        onDoneClick = setTaskDone,
                        {},
                        {}
                    )
                }
            }
        }
    }
}
