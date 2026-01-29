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

package dev.sergiobelda.todometer.common.android.extensions

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.profileinstaller.ProfileVerifier
import androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_COMPILED_WITH_PROFILE
import androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_COMPILED_WITH_PROFILE_NON_MATCHING
import androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ
import androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE
import androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_PACKAGE_NAME_DOES_NOT_EXIST
import androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_ERROR_UNSUPPORTED_API_VERSION
import androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_NO_PROFILE_INSTALLED
import androidx.profileinstaller.ProfileVerifier.CompilationStatus.RESULT_CODE_PROFILE_ENQUEUED_FOR_COMPILATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ComponentActivity.logCompilationStatus(name: String) {
    lifecycleScope.launch {
        withContext(Dispatchers.IO) {
            val status = ProfileVerifier.getCompilationStatusAsync().await()
            when (status.profileInstallResultCode) {
                RESULT_CODE_NO_PROFILE_INSTALLED -> {
                    Log.d(name, "ProfileInstaller: Baseline Profile not found")
                }

                RESULT_CODE_COMPILED_WITH_PROFILE -> {
                    Log.d(name, "ProfileInstaller: Compiled with profile")
                }

                RESULT_CODE_PROFILE_ENQUEUED_FOR_COMPILATION -> {
                    Log.d(name, "ProfileInstaller: Enqueued for compilation")
                }

                RESULT_CODE_COMPILED_WITH_PROFILE_NON_MATCHING -> {
                    Log.d(name, "ProfileInstaller: App was installed through Play store")
                }

                RESULT_CODE_ERROR_PACKAGE_NAME_DOES_NOT_EXIST -> {
                    Log.d(name, "ProfileInstaller: PackageName not found")
                }

                RESULT_CODE_ERROR_CACHE_FILE_EXISTS_BUT_CANNOT_BE_READ -> {
                    Log.d(name, "ProfileInstaller: Cache file exists but cannot be read")
                }

                RESULT_CODE_ERROR_CANT_WRITE_PROFILE_VERIFICATION_RESULT_CACHE_FILE -> {
                    Log.d(name, "ProfileInstaller: Can't write cache file")
                }

                RESULT_CODE_ERROR_UNSUPPORTED_API_VERSION -> {
                    Log.d(name, "ProfileInstaller: Enqueued for compilation")
                }

                else -> {
                    Log.d(name, "ProfileInstaller: Profile not compiled or enqueued")
                }
            }
        }
    }
}
