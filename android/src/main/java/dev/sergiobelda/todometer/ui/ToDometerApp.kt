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

package dev.sergiobelda.todometer.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.todometer.common.android.navigation.NavigationAction
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.ui.theme.ToDometerAppTheme
import org.koin.androidx.compose.getViewModel

@Composable
internal fun ToDometerApp(mainViewModel: MainViewModel = getViewModel()) {
    val navController = rememberNavController()
    val navigationAction = remember(navController) { NavigationAction(navController) }

    val appThemeState = mainViewModel.appTheme.collectAsState()
    val darkTheme: Boolean = when (appThemeState.value) {
        AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        AppTheme.DARK_THEME -> true
        AppTheme.LIGHT_THEME -> false
    }

    ToDometerAppTheme(darkTheme) {
        ToDometerNavHost(navController, navigationAction)
    }
}
