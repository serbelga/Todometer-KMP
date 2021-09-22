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

package com.sergiobelda.todometer.common.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.prefs.Preferences

actual class Preferences {
    private val preferences = Preferences.userRoot()?.node(javaClass.name)

    actual suspend fun set(key: String, value: String) {
        preferences?.put(key, value)
    }

    actual suspend fun set(key: String, value: Long) {
        preferences?.putLong(key, value)
    }

    actual suspend fun set(key: String, value: Int) {
        preferences?.putInt(key, value)
    }

    actual fun getString(key: String, default: String): Flow<String> = flow {
        emit(preferences?.get(key, default) ?: default)
    }

    actual fun getStringOrNull(key: String): Flow<String?> = flow {
        emit(preferences?.get(key, null))
    }

    actual fun getLong(key: String, default: Long): Flow<Long> = flow {
        emit(preferences?.getLong(key, default) ?: default)
    }

    actual fun getLongOrNull(key: String): Flow<Long?> = flow {
        // TODO This can throw java.lang.NumberFormatException
        emit(preferences?.get(key, null)?.toLong())
    }

    actual fun getInt(key: String, default: Int): Flow<Int> = flow {
        emit(preferences?.getInt(key, default) ?: default)
    }

    actual fun getIntOrNull(key: String): Flow<Int?> = flow {
        // TODO This can throw java.lang.NumberFormatException
        emit(preferences?.get(key, null)?.toInt())
    }
}
