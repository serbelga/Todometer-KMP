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

package dev.sergiobelda.todometer.gradle.buildlogic.convention.multiplatform.ui

import dev.sergiobelda.projectconfig.buildlogic.convention.extensions.deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CommonUiToolingPreviewConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                "androidRuntimeClasspath"(deps.findLibrary("jetbrains-compose-uiTooling").get())
            }

            val extension = extensions.getByType<KotlinMultiplatformExtension>()
            extension.apply {
                sourceSets.apply {
                    commonMain.dependencies {
                        implementation(deps.findLibrary("jetbrains-compose-uiToolingPreview").get())
                    }
                }
            }
        }
    }
}
