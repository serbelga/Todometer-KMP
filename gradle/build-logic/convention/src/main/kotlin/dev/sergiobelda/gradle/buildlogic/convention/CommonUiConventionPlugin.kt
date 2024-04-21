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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CommonUiAndroidConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                // TODO: Use libs.
                apply("com.google.devtools.ksp")
            }

            val extension = extensions.getByType<KotlinMultiplatformExtension>()
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add(
                    "kspCommonMainMetadata",
                    "dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-compiler:0.5.0-dev01"
                )
            }

            // Workaround for KSP only in Common Main.
            // https://github.com/google/ksp/issues/567
            tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
                if (name != "kspCommonMainKotlinMetadata") {
                    dependsOn("kspCommonMainKotlinMetadata")
                }
            }

            extension.sourceSets["commonMain"].kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
    }
}
