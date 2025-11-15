plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.gradle.buildlogic.convention"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
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
        register("commonLibraryAndroid") {
            id = libs.plugins.sergiobelda.gradle.common.library.android.get().pluginId
            implementationClass = conventionPluginsPath + "CommonLibraryAndroidConventionPlugin"
        }
        register("commonUi") {
            id = libs.plugins.sergiobelda.gradle.common.ui.get().pluginId
            implementationClass = conventionPluginsPath + "CommonUiConventionPlugin"
        }
        register("dependencyGraphGenerator") {
            id = libs.plugins.sergiobelda.gradle.dependencyGraphGenerator.get().pluginId
            implementationClass = conventionPluginsPath + "DependencyGraphGeneratorConventionPlugin"
        }

        val conventionPluginsLintPath = conventionPluginsPath + "lint."
        register("detekt") {
            id = libs.plugins.sergiobelda.gradle.detekt.get().pluginId
            implementationClass = conventionPluginsLintPath + "DetektConventionPlugin"
        }
        register("spotless") {
            id = libs.plugins.sergiobelda.gradle.spotless.get().pluginId
            implementationClass = conventionPluginsLintPath + "SpotlessConventionPlugin"
        }
        register("lint") {
            id = libs.plugins.sergiobelda.gradle.lint.get().pluginId
            implementationClass = conventionPluginsLintPath + "LintConventionPlugin"
        }
    }
}
