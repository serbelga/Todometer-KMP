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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.common.data.doIfSuccess
import com.sergiobelda.todometer.common.model.Project
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.usecase.GetProjectSelectedUseCase
import com.sergiobelda.todometer.common.usecase.GetTasksUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoingUseCase
import com.sergiobelda.todometer.common.usecase.SetTaskDoneUseCase
import com.sergiobelda.todometer.compose.ui.icons.iconToDometer
import com.sergiobelda.todometer.compose.ui.task.TaskItem
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
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
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = iconToDometer(),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp).padding(end = 8.dp)
                        )
                        Text(text = "ToDometer")
                    }
                },
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(
                        onClick = { scope.launch { scaffoldState.drawerState.open() } }
                    ) {
                        Icon(Icons.Rounded.Menu, contentDescription = "Menu")
                    }
                },
                backgroundColor = TodometerColors.surface
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
                onClick = addTask,
                backgroundColor = TodometerColors.primary
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
