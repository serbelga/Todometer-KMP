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

package dev.sergiobelda.todometer.common.ui.base.navigation

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.ui.base.BaseScreen
import dev.sergiobelda.todometer.common.ui.base.BaseState
import dev.sergiobelda.todometer.common.ui.base.BaseUIState
import dev.sergiobelda.todometer.common.ui.base.BaseViewModel

@Composable
inline fun <State : BaseState, UIState : BaseUIState, reified E : NavigationEvent>
    BaseScreen<State, UIState>.ScreenNavigationNode(
        viewModel: BaseViewModel<State>,
        navigationEventsHandler: NavigationEventsHandler<E>,
    ) {
    Screen(
        viewModel = viewModel,
        onEvent = {
            if (it is E) {
                navigationEventsHandler.handleNavigationEvent(it)
            }
        },
    )
}

@Composable
inline fun <State : BaseState, UIState : BaseUIState>
    BaseScreen<State, UIState>.ScreenNavigationNode(
        viewModel: BaseViewModel<State>,
    ) {
    ScreenNavigationNode(
        viewModel = viewModel,
        navigationEventsHandler = NavigationEventsHandler { },
    )
}
