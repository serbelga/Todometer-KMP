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

package dev.sergiobelda.gradle.buildlogic.convention.lint

import dev.sergiobelda.gradle.buildlogic.convention.extensions.libs
import dev.sergiobelda.gradle.buildlogic.convention.extensions.pluginId
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

/**
 * TODO
 */
class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("detekt").pluginId)
            }

            extensions.configure<DetektExtension> {
                tasks.withType<Detekt> {
                    setSource(files(project.projectDir))
                    exclude("**/build/**")
                    exclude {
                        it.file.relativeTo(projectDir).startsWith(
                            project.layout.buildDirectory.get().asFile.relativeTo(projectDir)
                        )
                    }
                }
            }
        }
    }
}
