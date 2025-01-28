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

package dev.sergiobelda.todometer.common.ui.base

import androidx.compose.runtime.Composable

abstract class BaseScreen<State : BaseState, UIState : BaseUIState> {

    private lateinit var eventHandler: EventHandler

    @Composable
    abstract fun rememberUIState(): UIState

    @Composable
    fun Screen(
        viewModel: BaseViewModel<State>,
        onEvent: (BaseEvent) -> Unit = {},
    ) {
        val uiState = rememberUIState()
        eventHandler = EventHandler { event ->
            viewModel.handleEvent(event)
            uiState.handleEvent(event)
            onEvent.invoke(event)
        }
        Content(
            state = viewModel.state,
            uiState = uiState,
        )
    }

    @Composable
    protected abstract fun Content(
        state: State,
        uiState: UIState,
    )

    fun onEvent(
        event: BaseEvent,
    ) {
        eventHandler.handleEvent(event)
    }
}
