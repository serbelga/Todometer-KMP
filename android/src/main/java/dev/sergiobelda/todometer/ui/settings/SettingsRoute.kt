/*
 * Copyright 2023 Sergio Belda
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

package dev.sergiobelda.todometer.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.sergiobelda.todometer.feature.settings.ui.SettingsScreen
import dev.sergiobelda.todometer.feature.settings.ui.SettingsViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun SettingsRoute(
    navigateBack: () -> Unit,
    settingsViewModel: SettingsViewModel = getViewModel()
) {
    val appTheme by settingsViewModel.appTheme.collectAsStateWithLifecycle()
    SettingsScreen(
        navigateBack = navigateBack,
        onChooseAppTheme = { settingsViewModel.setAppTheme(it) },
        appTheme = appTheme
    )
}
