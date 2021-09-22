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

expect class Preferences {

    suspend fun set(key: String, value: String)

    suspend fun set(key: String, value: Long)

    suspend fun set(key: String, value: Int)

    fun getString(key: String, default: String = ""): Flow<String>

    fun getStringOrNull(key: String): Flow<String?>

    fun getLong(key: String, default: Long = 1): Flow<Long>

    fun getLongOrNull(key: String): Flow<Long?>

    fun getInt(key: String, default: Int = 1): Flow<Int>

    fun getIntOrNull(key: String): Flow<Int?>
}
