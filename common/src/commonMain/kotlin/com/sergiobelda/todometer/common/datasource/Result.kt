package com.sergiobelda.todometer.common.datasource

// TODO: Move
sealed class Result<out A> {
    data class Success<out A>(val value: A?) : Result<A>()
    data class Error(
        val code: Int? = null,
        val error: String? = null,
        val exception: Throwable? = null
    ) : Result<Nothing>()
    object Loading : Result<Nothing>()

    fun <B> resultMap(m: ((A) -> B)): Result<B> = when (this) {
        is Success -> Success(this.value?.let { m(it) })
        is Error -> Error(this.code, this.error, this.exception)
        is Loading -> Loading
    }
}

/**
 * Call the specific action in [callback] if the result is [Result.Success] and not null.
 */
inline fun <reified A> Result<A>.doIfSuccess(callback: (value: A) -> Unit) {
    (this as? Result.Success)?.value?.let { callback(it) }
}

/**
 * Call the specific action in [callback] if the result is [Result.Error].
 */
inline fun <reified A> Result<A>.doIfError(callback: (code: Int?, error: String?, exception: Throwable?) -> Unit) {
    (this as? Result.Error)?.let { callback(it.code, it.error, it.exception) }
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
