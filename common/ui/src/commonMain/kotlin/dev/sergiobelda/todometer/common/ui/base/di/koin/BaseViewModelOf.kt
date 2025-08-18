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

import dev.sergiobelda.todometer.common.ui.base.BaseUIState
import dev.sergiobelda.todometer.common.ui.base.BaseViewModel
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.new
import org.koin.core.module.dsl.onOptions

/**
 * Declare a [Module.baseViewModel] definition by resolving a constructor reference for the dependency.
 * The resolution is done at compile time by leveraging inline functions, no reflection is required.
 *
 * ```kotlin
 * class AboutViewModel : BaseViewModel<AboutUIState>(initialUIState = AboutUIState)
 *
 * val aboutViewModelModule = module {
 *      baseViewModelOf(::AboutViewModel)
 * }
 * ```
 */
inline fun <reified U : BaseUIState> Module.baseViewModelOf(
    crossinline constructor: () -> BaseViewModel<U>,
    noinline options: (BeanDefinition<BaseViewModel<U>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<U>> = baseViewModel { new(constructor) }.onOptions(options)

inline fun <reified U : BaseUIState, reified T1> Module.baseViewModelOf(
    crossinline constructor: (T1) -> BaseViewModel<U>,
    noinline options: (BeanDefinition<BaseViewModel<U>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<U>> = baseViewModel { new(constructor) }.onOptions(options)

inline fun <reified U : BaseUIState, reified T1, reified T2> Module.baseViewModelOf(
    crossinline constructor: (T1, T2) -> BaseViewModel<U>,
    noinline options: (BeanDefinition<BaseViewModel<U>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<U>> = baseViewModel { new(constructor) }.onOptions(options)

inline fun <reified U : BaseUIState, reified T1, reified T2, reified T3> Module.baseViewModelOf(
    crossinline constructor: (T1, T2, T3) -> BaseViewModel<U>,
    noinline options: (BeanDefinition<BaseViewModel<U>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<U>> = baseViewModel { new(constructor) }.onOptions(options)
