package dev.sergiobelda.gradle.buildlogic.convention.multiplatform.ui

import dev.sergiobelda.gradle.buildlogic.convention.extensions.deps
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
