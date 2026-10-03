plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.gradle.buildlogic.convention"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.dependencyGraphGenerator)
    implementation(libs.jetbrains.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        val conventionPluginsPath = "dev.sergiobelda.gradle.buildlogic.convention."
        register("dependencyGraphGenerator") {
            id = libs.plugins.sergiobelda.gradle.dependencyGraphGenerator.get().pluginId
            implementationClass = conventionPluginsPath + "DependencyGraphGeneratorConventionPlugin"
        }

        val conventionPluginsMultiplatformPath = conventionPluginsPath + "multiplatform."

        val conventionPluginsMultiplatformAndroidPath = conventionPluginsMultiplatformPath + "android."
        register("commonLibraryAndroid") {
            id = libs.plugins.sergiobelda.gradle.common.library.android.get().pluginId
            implementationClass = conventionPluginsMultiplatformAndroidPath + "CommonLibraryAndroidConventionPlugin"
        }

        val conventionPluginsMultiplatformUiPath = conventionPluginsMultiplatformPath + "ui."
        register("commonUi") {
            id = libs.plugins.sergiobelda.gradle.common.ui.get().pluginId
            implementationClass = conventionPluginsMultiplatformUiPath + "CommonUiConventionPlugin"
        }
        register("commonUiToolingPreview") {
            id = libs.plugins.sergiobelda.gradle.common.uiToolingPreview.get().pluginId
            implementationClass = conventionPluginsMultiplatformUiPath + "CommonUiToolingPreviewConventionPlugin"
        }
    }
}
