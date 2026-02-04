plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.gradle.buildlogic.convention"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.dependencyGraphGenerator)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.jetbrains.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        val conventionPluginsPath = "dev.sergiobelda.gradle.buildlogic.convention."
        register("baseLibrary") {
            id = libs.plugins.sergiobelda.gradle.base.get().pluginId
            implementationClass = conventionPluginsPath + "BaseConventionPlugin"
        }
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

        val conventionPluginsLintPath = conventionPluginsPath + "lint."
        register("detekt") {
            id = libs.plugins.sergiobelda.gradle.detekt.get().pluginId
            implementationClass = conventionPluginsLintPath + "DetektConventionPlugin"
        }
        register("lint") {
            id = libs.plugins.sergiobelda.gradle.lint.get().pluginId
            implementationClass = conventionPluginsLintPath + "LintConventionPlugin"
        }
        register("spotless") {
            id = libs.plugins.sergiobelda.gradle.spotless.get().pluginId
            implementationClass = conventionPluginsLintPath + "SpotlessConventionPlugin"
        }
    }
}
