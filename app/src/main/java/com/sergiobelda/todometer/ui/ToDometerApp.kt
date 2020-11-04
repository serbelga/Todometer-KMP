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

package com.sergiobelda.todometer.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import com.sergiobelda.todometer.ui.addproject.AddProjectScreen
import com.sergiobelda.todometer.ui.addtask.AddTaskScreen
import com.sergiobelda.todometer.ui.home.HomeScreen
import com.sergiobelda.todometer.ui.taskdetail.TaskDetailScreen
import com.sergiobelda.todometer.ui.theme.ToDometerTheme
import com.sergiobelda.todometer.ui.utils.Navigator
import com.sergiobelda.todometer.viewmodel.MainViewModel

@Composable
fun ToDometerApp(backDispatcher: OnBackPressedDispatcher, mainViewModel: MainViewModel) {
    val navigator = rememberSavedInstanceState(
        saver = Navigator.saver<Destination>(backDispatcher)
    ) {
        Navigator(Destination.Home, backDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }
    ToDometerTheme {
        Crossfade(navigator.current) { destination ->
            when (destination) {
                Destination.Home -> HomeScreen(
                    mainViewModel = mainViewModel,
                    addTask = actions.addTask,
                    addProject = actions.addProject,
                    openTask = actions.selectTask
                )
                is Destination.AddTask -> AddTaskScreen(
                    mainViewModel = mainViewModel,
                    upPress = actions.upPress
                )
                is Destination.AddProject -> AddProjectScreen(
                    mainViewModel = mainViewModel,
                    upPress = actions.upPress
                )
                is Destination.TaskDetail -> TaskDetailScreen(
                    taskId = destination.taskId,
                    mainViewModel = mainViewModel,
                    upPress = actions.upPress
                )
            }
        }
    }
}
