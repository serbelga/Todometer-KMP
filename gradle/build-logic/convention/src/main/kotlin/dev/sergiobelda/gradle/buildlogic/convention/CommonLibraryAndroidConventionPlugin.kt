package dev.sergiobelda.gradle.buildlogic.convention
/*
 * Copyright 2023 Sergio Belda
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

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import dev.sergiobelda.gradle.buildlogic.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CommonLibraryAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("android-kotlinMultiplatformLibrary").get().get().pluginId)
            }

            val extension = extensions.getByType<KotlinMultiplatformExtension>()
            // TODO: Replace by androidLibrary when "Unresolved reference 'androidLibrary'" is fixed.
            extension.extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                compileSdk = libs.findVersion("androidCompileSdk").get().toString().toInt()
                minSdk = libs.findVersion("androidMinSdk").get().toString().toInt()
            }
        }
    }
}
