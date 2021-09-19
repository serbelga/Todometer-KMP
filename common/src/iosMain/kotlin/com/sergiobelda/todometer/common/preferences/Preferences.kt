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
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

actual class Preferences {
    private val preferences = NSUserDefaults.standardUserDefaults()

    actual suspend fun set(key: String, value: String) {
        preferences.setValue(value, forKey = key)
        preferences.synchronize()
    }

    actual suspend fun set(key: String, value: Long) {
        // TODO("Not yet implemented")
    }

    actual fun getString(key: String, default: String): Flow<String> = flow {
        // TODO("Not yet implemented")
        emit("")
    }

    actual fun getStringOrNull(key: String): Flow<String?> = flow {
        // TODO("Not yet implemented")
        emit("")
    }

    actual fun getLong(key: String, default: Long): Flow<Long> = flow {
        // TODO("Not yet implemented")
        emit(1)
    }

    actual fun getLongOrNull(key: String): Flow<Long?> = flow {
        // TODO("Not yet implemented")
        emit(1)
    }

    actual fun getInt(key: String, default: Int): Flow<Int> = flow {
        // TODO("Not yet implemented")
        emit(1)
    }

    actual fun getIntOrNull(key: String): Flow<Int?> = flow {
        // TODO("Not yet implemented")
        emit(1)
    }
}
