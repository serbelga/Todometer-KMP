/*
 * Copyright 2024 Sergio Belda
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.navigation.compose.extended.rememberNavAction
import dev.sergiobelda.todometer.app.common.ui.theme.TodometerAppTheme
import dev.sergiobelda.todometer.app.navhost.TodometerNavHost
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.domain.usecase.apptheme.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.resources.ProvideTodometerStrings
import org.koin.compose.koinInject

@Composable
fun TodometerApp() {
    ProvideTodometerStrings {
        val getAppThemeUseCase = koinInject<GetAppThemeUseCase>()
        val appThemeState = getAppThemeUseCase().collectAsState(AppTheme.FOLLOW_SYSTEM)
        val darkTheme: Boolean = when (appThemeState.value) {
            AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            AppTheme.DARK_THEME -> true
            AppTheme.LIGHT_THEME -> false
        }
        TodometerAppTheme(darkTheme) {
            val navController = rememberNavController()
            val navAction = rememberNavAction(navController)
            TodometerNavHost(
                navController = navController,
                navAction = navAction,
            )
        }
    }
}
