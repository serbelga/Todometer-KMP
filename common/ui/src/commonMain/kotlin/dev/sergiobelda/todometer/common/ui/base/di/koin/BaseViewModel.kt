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
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import dev.sergiobelda.todometer.common.ui.base.BaseUIState
import dev.sergiobelda.todometer.common.ui.base.BaseViewModel
import org.koin.compose.currentKoinScope
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.onOptions
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.qualifier
import org.koin.core.scope.Scope
import org.koin.viewmodel.defaultExtras

inline fun <reified T : BaseViewModel<*>> Module.baseViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>,
): KoinDefinition<T> = factory(qualifier, definition)

inline fun <reified U : BaseUIState> Module.baseViewModelOf(
    crossinline constructor: () -> BaseViewModel<U>,
    noinline options: (BeanDefinition<BaseViewModel<U>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<U>> = viewModelOf(constructor) {
    named<U>()
}.onOptions(options)

inline fun <reified U : BaseUIState, reified T1> Module.baseViewModelOf(
    crossinline constructor: (T1) -> BaseViewModel<U>,
    noinline options: (BeanDefinition<BaseViewModel<U>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<U>> = viewModelOf(constructor) {
    named<U>()
}.onOptions(options)

inline fun <reified U : BaseUIState, reified T1, reified T2> Module.baseViewModelOf(
    crossinline constructor: (T1, T2) -> BaseViewModel<U>,
    noinline options: (BeanDefinition<BaseViewModel<U>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<U>> = viewModelOf(constructor) {
    named<U>()
}.onOptions(options)

@OptIn(KoinInternalApi::class)
@Composable
inline fun <reified U : BaseUIState> koinBaseViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = LocalViewModelStoreOwner.current
        ?: error("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"),
    key: String? = null,
    extras: CreationExtras = defaultExtras(viewModelStoreOwner),
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
): BaseViewModel<U> =
    koinViewModel(
        qualifier = qualifier<U>(),
        viewModelStoreOwner = viewModelStoreOwner,
        key = key,
        extras = extras,
        scope = scope,
        parameters = parameters,
    )
