/*
 * Copyright 2025 Sergio Belda
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

package dev.sergiobelda.todometer.app.feature.home.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.annotation.RememberInComposition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.sergiobelda.fonament.presentation.ui.FonamentContentState
import dev.sergiobelda.fonament.presentation.ui.FonamentEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
data class HomeContentState
    @RememberInComposition
    constructor(
        val drawerState: DrawerState,
        val snackbarHostState: SnackbarHostState,
        val coroutineScope: CoroutineScope,
    ) : FonamentContentState {
        var swipedTaskId by mutableStateOf("")
            private set

        var deleteTaskAlertDialogState by mutableStateOf(false)
            private set

        var deleteTasksAlertDialogState by mutableStateOf(false)
            private set

        var deleteTaskListAlertDialogState by mutableStateOf(false)
            private set

        var homeMoreDropdownExpanded by mutableStateOf(false)
            private set

        override fun handleEvent(event: FonamentEvent) {
            when (event) {
                HomeEvent.OpenDrawer -> {
                    coroutineScope.launch { drawerState.open() }
                }

                HomeEvent.CloseDrawer -> {
                    coroutineScope.launch { drawerState.close() }
                }

                HomeEvent.ShowHomeMoreDropdown -> {
                    homeMoreDropdownExpanded = true
                }

                HomeEvent.DismissHomeMoreDropdown -> {
                    homeMoreDropdownExpanded = false
                }

                HomeEvent.ShowDeleteTaskListAlertDialog -> {
                    deleteTaskListAlertDialogState = true
                }

                HomeEvent.DismissDeleteTaskListAlertDialog -> {
                    deleteTaskListAlertDialogState = false
                }

                is HomeEvent.ShowDeleteTaskAlertDialog -> {
                    deleteTaskAlertDialogState = true
                    swipedTaskId = event.id
                }

                HomeEvent.DismissDeleteTaskAlertDialog -> {
                    deleteTaskAlertDialogState = false
                    swipedTaskId = ""
                }

                HomeEvent.ShowDeleteTasksAlertDialog -> {
                    deleteTasksAlertDialogState = true
                }

                HomeEvent.DismissDeleteTasksAlertDialog -> {
                    deleteTasksAlertDialogState = false
                }
            }
        }
    }

@Composable
fun rememberHomeContentState(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): HomeContentState =
    remember {
        HomeContentState(
            drawerState = drawerState,
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
        )
    }
