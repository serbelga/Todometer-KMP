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

package com.sergiobelda.todometer.common.api

import com.sergiobelda.todometer.common.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = ApplicationDispatcher,
    apiCall: suspend () -> T
): Result<T> = withContext(dispatcher) {
    try {
        val response = apiCall.invoke()
        Result.Success<T>(response)
    } catch (throwable: Throwable) {
        // TODO Update code error
        when (throwable) {
            // is IOException -> Result.Error
            else -> {
                Result.Error(null, null, throwable)
            }
        }
    }
}
