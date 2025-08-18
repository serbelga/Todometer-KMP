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

package dev.sergiobelda.todometer.common.ui.base.di.koin

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.ui.base.BaseUI
import dev.sergiobelda.todometer.common.ui.base.BaseUIState
import dev.sergiobelda.todometer.common.ui.base.navigation.BaseNavigationEvent
import dev.sergiobelda.todometer.common.ui.base.navigation.BaseNavigationEventHandler
import dev.sergiobelda.todometer.common.ui.base.navigation.NavigationNodeContent
import org.koin.core.parameter.ParametersDefinition

@Composable
inline fun <reified N, reified U> BaseUI<U, *>.KoinNavigationNodeContent(
    noinline parameters: ParametersDefinition? = null,
    navigationEventHandler: BaseNavigationEventHandler<N>,
) where N : BaseNavigationEvent, U : BaseUIState {
    NavigationNodeContent(
        viewModel = koinBaseViewModel(parameters = parameters),
        navigationEventHandler = navigationEventHandler,
    )
}
