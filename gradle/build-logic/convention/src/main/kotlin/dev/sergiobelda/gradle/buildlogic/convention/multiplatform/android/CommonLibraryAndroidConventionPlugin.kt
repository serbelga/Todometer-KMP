package dev.sergiobelda.gradle.buildlogic.convention.multiplatform.android

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
