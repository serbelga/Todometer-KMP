package dev.sergiobelda.gradle.buildlogic.convention.multiplatform.ui

import dev.sergiobelda.gradle.buildlogic.convention.extensions.libs
import dev.sergiobelda.gradle.buildlogic.convention.extensions.pluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

class CommonUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("google-ksp").pluginId)
            }

            val navigationComposeExtendedCompiler =
                libs.findLibrary("sergiobelda.navigationComposeExtendedCompiler").get().get()
                    .toString()

            dependencies {
                add("kspCommonMainMetadata", navigationComposeExtendedCompiler)
            }

            // Workaround for KSP only in Common Main.
            // https://github.com/google/ksp/issues/567
            tasks.withType<KotlinCompilationTask<*>>().all {
                if (name != "kspCommonMainKotlinMetadata") {
                    dependsOn("kspCommonMainKotlinMetadata")
                }
            }

            val extension = extensions.getByType<KotlinMultiplatformExtension>()
            extension.apply {
                sourceSets["commonMain"].kotlin.srcDir(
                    "build/generated/ksp/metadata/commonMain/kotlin"
                )

                sourceSets.apply {
                    commonMain.dependencies {
                        implementation(libs.findLibrary("jetbrains-kotlin-collections-immutable").get())
                        implementation(libs.findLibrary("sergiobelda-fonament-presentation").get())
                        implementation(libs.findLibrary("sergiobelda-fonament-presentationDiKoin").get())
                    }
                }
            }
        }
    }
}
