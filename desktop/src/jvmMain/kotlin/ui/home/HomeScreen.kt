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

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.common.data.doIfSuccess
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.GetTasksUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import com.sergiobelda.todometer.compose.ui.task.TaskItem
import koin
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(addTask: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val setTaskDoingUseCase = koin.get<SetTaskDoingUseCase>()
    val setTaskDoneUseCase = koin.get<SetTaskDoneUseCase>()
    val getProjectSelectedUseCase = koin.get<GetProjectSelectedUseCase>()
    val getTasksUseCase = koin.get<GetTasksUseCase>()
    val coroutineScope = rememberCoroutineScope()
    val setTaskDoing: (String) -> Unit = {
        coroutineScope.launch {
            setTaskDoingUseCase(it)
        }
    }
    val setTaskDone: (String) -> Unit = {
        coroutineScope.launch {
            setTaskDoneUseCase(it)
        }
    }
    var project: Project? by remember { mutableStateOf(null) }
    val projectResultState by getProjectSelectedUseCase().collectAsState(null)
    projectResultState?.let { result ->
        result.doIfSuccess { project = it }
    }
    var tasks: List<Task> by remember { mutableStateOf(emptyList()) }
    val tasksResultState by getTasksUseCase().collectAsState(null)
    tasksResultState?.let { result ->
        result.doIfSuccess { tasks = it }
    }
    Scaffold(
        drawerShape = RoundedCornerShape(0),
        drawerContent = {
            DrawerContainer()
        },
        topBar = {
            // ToDometerTopAppBar(project, tasks)
            TopAppBar(
                title = {},
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(
                        onClick = { scope.launch { scaffoldState.drawerState.open() } }
                    ) {
                        Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = {
                    Icon(Icons.Rounded.Add, "Add task")
                },
                text = {
                    Text("Add task")
                },
                onClick = addTask
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        scaffoldState = scaffoldState
    ) {
        LazyColumn {
            items(tasks) {
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

@Composable
fun DrawerContainer() {
    TextButton(onClick = {}) {
        Icon(Icons.Rounded.Add, contentDescription = "Add project")
        Text(text = "Add project")
    }
}
