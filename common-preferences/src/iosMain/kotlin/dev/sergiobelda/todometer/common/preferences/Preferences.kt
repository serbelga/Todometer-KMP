package dev.sergiobelda.todometer.common.preferences

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

    actual suspend fun set(key: String, value: Int) {
        // TODO("Not yet implemented")
    }

    actual fun getString(key: String): Flow<String?> = flow {
        // TODO("Not yet implemented")
        emit("")
    }

    actual fun getStringOrDefault(key: String, default: String): Flow<String> = flow {
        // TODO("Not yet implemented")
        emit("")
    }

    actual fun getLong(key: String): Flow<Long?> = flow {
        // TODO("Not yet implemented")
        emit(1)
    }

    actual fun getLongOrDefault(key: String, default: Long): Flow<Long> = flow {
        // TODO("Not yet implemented")
        emit(1)
    }

    actual fun getInt(key: String): Flow<Int?> = flow {
        // TODO("Not yet implemented")
        emit(1)
    }

    actual fun getIntOrDefault(key: String, default: Int): Flow<Int> = flow {
        // TODO("Not yet implemented")
        emit(1)
    }
}
