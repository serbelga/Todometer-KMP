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

package dev.sergiobelda.todometer.common.domain.extensions

// TODO: Move to new module
/**
 * Returns an enum entry or null with specified [name].
 */
inline fun <reified T : Enum<T>> safeEnumValueOf(name: String): T? =
    enumValues<T>().firstOrNull { it.name.equals(name, ignoreCase = true) }

/**
 * Returns an enum entry with specified [name] or [default].
 */
inline fun <reified T : Enum<T>> enumValueOrDefaultOf(name: String, default: T): T =
    enumValues<T>().firstOrNull { it.name.equals(name, ignoreCase = true) } ?: default
