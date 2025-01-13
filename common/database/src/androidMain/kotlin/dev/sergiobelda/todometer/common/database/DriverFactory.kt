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

package dev.sergiobelda.todometer.common.database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.sergiobelda.todometer.common.database.DriverFactory.appContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

actual object DriverFactory {
    lateinit var appContext: Context

    actual fun createDriver(): SqlDriver {
        val database: File = appContext.getDatabasePath(FILENAME)

        if (!database.exists()) {
            openDatabaseFile(
                absolutePath = database.absolutePath,
            )
        }

        return AndroidSqliteDriver(
            TodometerDatabase.Schema,
            appContext,
            FILENAME,
            callback = object : AndroidSqliteDriver.Callback(TodometerDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.execSQL("PRAGMA foreign_keys = ON;")
                }
            },
        )
    }
}

private fun openDatabaseFile(
    absolutePath: String,
) {
    try {
        val inputStream = appContext.assets.open(FILENAME)
        val outputStream = FileOutputStream(absolutePath)

        inputStream.use { input ->
            outputStream.use {
                input.copyTo(it)
            }
        }
    } catch (_: FileNotFoundException) {
    }
}

private const val FILENAME: String = "todometer.db"
