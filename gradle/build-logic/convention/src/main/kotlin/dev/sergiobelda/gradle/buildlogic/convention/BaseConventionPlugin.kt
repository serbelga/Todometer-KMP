package dev.sergiobelda.gradle.buildlogic.convention

import dev.sergiobelda.gradle.buildlogic.convention.extensions.libs
import dev.sergiobelda.gradle.buildlogic.convention.extensions.pluginId
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

class BaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("detekt").pluginId)
                apply(SpotlessConventionPlugin::class.java)
            }

            extensions.configure<DetektExtension> {
                tasks.withType<Detekt> {
                    setSource(files(project.projectDir))
                    exclude("**/build/**")
                    exclude {
                        it.file.relativeTo(projectDir).startsWith(project.buildDir.relativeTo(projectDir))
                    }
                }
                /*
                tasks.register("detektAll") {
                    dependsOn(tasks.withType<Detekt>())
                }
                */
            }
        }
    }
}
