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

package dev.sergiobelda.todometer.app.feature.settings.ui

import androidx.lifecycle.viewModelScope
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.domain.usecase.apptheme.GetAppThemeUseCase
import dev.sergiobelda.todometer.common.domain.usecase.apptheme.SetAppThemeUseCase
import dev.sergiobelda.todometer.common.ui.base.BaseEvent
import dev.sergiobelda.todometer.common.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    getAppThemeUseCase: GetAppThemeUseCase,
    private val setAppThemeUseCase: SetAppThemeUseCase,
) : BaseViewModel<SettingsUIState>(
    initialUIState = SettingsUIState(),
) {
    private val appTheme: StateFlow<AppTheme> =
        getAppThemeUseCase().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            AppTheme.FOLLOW_SYSTEM,
        )

    init {
        viewModelScope.launch {
            appTheme.collect { appTheme ->
                updateUIState { state ->
                    state.copy(appTheme = appTheme)
                }
            }
        }
    }

    override fun handleEvent(event: BaseEvent) {
        when (event) {
            is SettingsEvent.SetAppTheme -> {
                setAppTheme(event)
            }
        }
    }

    private fun setAppTheme(
        event: SettingsEvent.SetAppTheme,
    ) = viewModelScope.launch {
        setAppThemeUseCase(event.theme)
    }
}
