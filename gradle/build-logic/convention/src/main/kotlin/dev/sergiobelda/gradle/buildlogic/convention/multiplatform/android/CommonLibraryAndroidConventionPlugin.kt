package dev.sergiobelda.gradle.buildlogic.convention.multiplatform.android

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import dev.sergiobelda.gradle.buildlogic.convention.extensions.deps
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
                apply(deps.findPlugin("android-kotlinMultiplatformLibrary").get().get().pluginId)
            }

            val extension = extensions.getByType<KotlinMultiplatformExtension>()
            // TODO: Replace by androidLibrary when "Unresolved reference 'androidLibrary'" is fixed.
            extension.extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                compileSdk = deps.findVersion("android-compileSdk").get().toString().toInt()
                minSdk = deps.findVersion("android-minSdk").get().toString().toInt()
            }
        }
    }
}
