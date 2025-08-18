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

package dev.sergiobelda.todometer.common.ui.base.di

import dev.sergiobelda.todometer.common.ui.base.BaseViewModel
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.Qualifier

inline fun <reified T : BaseViewModel<*>> Module.baseViewModel(
    qualifier: Qualifier? = null,
    noinline definition: Definition<T>,
): KoinDefinition<T> = factory(qualifier, definition)

inline fun <reified V : BaseViewModel<*>> Module.baseViewModelOf(
    crossinline constructor: () -> V,
    noinline options: (BeanDefinition<V>.() -> Unit)? = null,
): KoinDefinition<V> = viewModelOf(constructor, options)

inline fun <reified V : BaseViewModel<*>, reified T1> Module.baseViewModelOf(
    crossinline constructor: (T1) -> V,
    noinline options: (BeanDefinition<V>.() -> Unit)? = null,
): KoinDefinition<V> = viewModelOf(constructor, options)

inline fun <reified V : BaseViewModel<*>, reified T1, reified T2> Module.baseViewModelOf(
    crossinline constructor: (T1, T2) -> V,
    noinline options: (BeanDefinition<V>.() -> Unit)? = null,
): KoinDefinition<V> = viewModelOf(constructor, options)
