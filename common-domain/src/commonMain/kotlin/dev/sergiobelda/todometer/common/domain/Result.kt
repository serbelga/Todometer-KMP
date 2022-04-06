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

package dev.sergiobelda.todometer.common.domain

sealed class Result<out A> {
    data class Success<out A>(val value: A) : Result<A>()
    data class Error(
        val code: Int? = null,
        val message: String? = null,
        val exception: Throwable? = null
    ) : Result<Nothing>()

    fun <B> map(m: ((A) -> B)): Result<B> = when (this) {
        is Success -> Success(m(this.value))
        is Error -> Error(this.code, this.message, this.exception)
    }
}

/**
 * Call the specific action in [callback] if the result is [Result.Success] and not null.
 */
inline fun <reified A> Result<A>.doIfSuccess(callback: (value: A) -> Unit): Result<A> {
    (this as? Result.Success)?.value?.let { callback(it) }
    return this
}

/**
 * Call the specific action in [callback] if the result is [Result.Error].
 */
inline fun <reified A> Result<A>.doIfError(callback: (error: Result.Error) -> Unit): Result<A> {
    (this as? Result.Error)?.let { callback(it) }
    return this
}

/**
 * Specify a default value if a given result is not [Result.Success], otherwise it
 * returns the success value.
 */
inline fun <A> Result<A>.withDefault(default: () -> A): Result.Success<A> {
    return when (this) {
        is Result.Success -> this
        else -> Result.Success(default())
    }
}
