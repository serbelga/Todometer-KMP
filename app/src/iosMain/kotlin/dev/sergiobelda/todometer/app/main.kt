/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.todometer.app.common.ui.theme.TodometerAppTheme
import dev.sergiobelda.todometer.app.feature.addtask.di.addTaskViewModelModule
import dev.sergiobelda.todometer.app.feature.addtasklist.di.addTaskListViewModelModule
import dev.sergiobelda.todometer.app.feature.edittask.di.editTaskViewModelModule
import dev.sergiobelda.todometer.app.feature.edittasklist.di.editTaskListViewModelModule
import dev.sergiobelda.todometer.app.feature.home.di.homeViewModelModule
import dev.sergiobelda.todometer.app.feature.settings.di.settingsViewModelModule
import dev.sergiobelda.todometer.app.feature.taskdetails.di.taskDetailsViewModelModule
import dev.sergiobelda.todometer.common.core.di.startAppDI
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.domain.usecase.apptheme.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.navigation.Action
import dev.sergiobelda.todometer.common.resources.ProvideTodometerStrings
import platform.UIKit.UIViewController

val koin = startAppDI {
    modules(
        addTaskViewModelModule +
            addTaskListViewModelModule +
            editTaskViewModelModule +
            editTaskListViewModelModule +
            homeViewModelModule +
            settingsViewModelModule +
            taskDetailsViewModelModule
    )
}.koin

fun MainViewController(): UIViewController = ComposeUIViewController {
    val getAppThemeUseCase = koin.get<GetAppThemeUseCase>()
    val appTheme by getAppThemeUseCase.invoke().collectAsState(AppTheme.DARK_THEME)
    val darkTheme: Boolean = when (appTheme) {
        AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        AppTheme.DARK_THEME -> true
        AppTheme.LIGHT_THEME -> false
    }
    val navController = rememberNavController()
    val action = remember(navController) { Action(navController) }
    ProvideTodometerStrings {
        TodometerAppTheme(darkTheme) {
            TodometerNavHost(
                navController = navController,
                action = action,
                navigateBackAction = {
                    action.navigateUp()
                }
            )
        }
    }
}
