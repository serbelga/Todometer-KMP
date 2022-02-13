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

object Versions {
    const val accompanist = "0.23.0"
    const val activityKtx = "1.4.0"
    const val appCompat = "1.4.1"
    const val androidGradlePlugin = "7.2.0-beta02"
    const val compose = "1.1.0"
    const val composeMultiplatform = "1.1.0-alpha03"
    const val dataStorePreferences = "1.0.0"
    const val espressoCore = "3.4.0"
    const val exposedSql = "0.32.1"
    const val junit = "4.12"
    const val androidJunit = "1.1.3"
    const val koin = "3.1.4"
    const val kotlin = "1.6.10"
    const val kotlinCoroutinesTest = "1.6.0"
    const val ktLint = "0.43.2"
    const val ktor = "1.6.7"
    const val ktxVersion = "1.7.0"
    const val lifecycle = "2.4.1"
    const val materialComponents = "1.4.0"
    const val mockk = "1.12.1"
    const val navigationCompose = "2.4.0-beta01"
    const val ossLicenses = "17.0.0"
    const val ossLicensesPlugin = "0.10.4"
    const val playServicesWearable = "17.1.0"
    const val robolectric = "4.3.1"
    const val sqlDelight = "1.5.2"
    const val testCoreKtx = "1.4.0"
    const val timber = "4.7.1"
    const val wear = "1.2.0"
    const val wearCompose = "1.0.0-alpha16"
    const val wearInput = "1.2.0-alpha02"
}

object Android {
    const val compileSdk = 31
    const val minSdk = 24
    const val wearMinSdk = 25
    const val targetSdk = 31
}

object Libs {

    const val ktLint = "com.pinterest:ktlint:${Versions.ktLint}"

    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"

    object AndroidX {
        const val junitKtx = "androidx.test.ext:junit-ktx:${Versions.androidJunit}"
        const val testCoreKtx = "androidx.test:core-ktx:${Versions.testCoreKtx}"

        object Test {
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
            const val extJunit = "androidx.test.ext:junit:${Versions.androidJunit}"
        }
    }

    object Google {

        object Services {
            const val ossLicensesPlugin =
                "com.google.android.gms:oss-licenses-plugin:${Versions.ossLicensesPlugin}"

            const val ossLicenses =
                "com.google.android.gms:play-services-oss-licenses:${Versions.ossLicenses}"

            const val wearable =
                "com.google.android.gms:play-services-wearable:${Versions.playServicesWearable}"
        }
    }
}
