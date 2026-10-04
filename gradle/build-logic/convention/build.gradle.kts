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
    implementation(deps.android.gradlePlugin)
    implementation(deps.jetbrains.kotlin.gradlePlugin)
    implementation(libs.dependencyGraphGenerator)
    implementation(libs.sergiobelda.projectconfig.convention)
}

gradlePlugin {
    plugins {
        val conventionPluginsPath = "dev.sergiobelda.todometer.gradle.buildlogic.convention."
        register("dependencyGraphGenerator") {
            id = libs.plugins.sergiobelda.todometer.dependencyGraphGenerator.get().pluginId
            implementationClass = conventionPluginsPath + "DependencyGraphGeneratorConventionPlugin"
        }

        val conventionPluginsMultiplatformPath = conventionPluginsPath + "multiplatform."

        val conventionPluginsMultiplatformAndroidPath = conventionPluginsMultiplatformPath + "android."
        register("commonLibraryAndroid") {
            id = libs.plugins.sergiobelda.todometer.common.library.android.get().pluginId
            implementationClass = conventionPluginsMultiplatformAndroidPath + "CommonLibraryAndroidConventionPlugin"
        }

        val conventionPluginsMultiplatformUiPath = conventionPluginsMultiplatformPath + "ui."
        register("commonUi") {
            id = libs.plugins.sergiobelda.todometer.common.ui.get().pluginId
            implementationClass = conventionPluginsMultiplatformUiPath + "CommonUiConventionPlugin"
        }
        register("commonUiToolingPreview") {
            id = libs.plugins.sergiobelda.todometer.common.uiToolingPreview.get().pluginId
            implementationClass = conventionPluginsMultiplatformUiPath + "CommonUiToolingPreviewConventionPlugin"
        }
    }
}
