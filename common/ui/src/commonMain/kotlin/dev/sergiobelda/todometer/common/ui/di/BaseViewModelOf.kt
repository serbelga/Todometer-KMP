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

package dev.sergiobelda.todometer.common.ui.di

import dev.sergiobelda.todometer.common.ui.base.BaseState
import dev.sergiobelda.todometer.common.ui.base.BaseViewModel
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.onOptions
import org.koin.core.module.dsl.viewModelOf

inline fun <reified S : BaseState> Module.baseViewModelOf(
    crossinline constructor: () -> BaseViewModel<S>,
    noinline options: (BeanDefinition<BaseViewModel<S>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<S>> = viewModelOf(constructor) {
    bind<BaseViewModel<S>>()
}.onOptions(options)

inline fun <reified S : BaseState, reified T1> Module.baseViewModelOf(
    crossinline constructor: (T1) -> BaseViewModel<S>,
    noinline options: (BeanDefinition<BaseViewModel<S>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<S>> = viewModelOf(constructor) {
    bind<BaseViewModel<S>>()
}.onOptions(options)

inline fun <reified S : BaseState, reified T1, reified T2> Module.baseViewModelOf(
    crossinline constructor: (T1, T2) -> BaseViewModel<S>,
    noinline options: (BeanDefinition<BaseViewModel<S>>.() -> Unit)? = null,
): KoinDefinition<BaseViewModel<S>> = viewModelOf(constructor) {
    bind<BaseViewModel<S>>()
}.onOptions(options)
