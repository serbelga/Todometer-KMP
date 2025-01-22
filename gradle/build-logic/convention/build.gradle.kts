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
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        val conventionPluginsPath = "dev.sergiobelda.gradle.buildlogic.convention."
        register("baseLibrary") {
            id = "dev.sergiobelda.gradle.base"
            implementationClass = conventionPluginsPath + "BaseConventionPlugin"
        }
        register("commonLibraryAndroid") {
            id = libs.plugins.dev.sergiobelda.gradle.common.library.android.get().pluginId
            implementationClass = conventionPluginsPath + "CommonLibraryAndroidConventionPlugin"
        }
        register("commonUi") {
            id = libs.plugins.dev.sergiobelda.gradle.common.ui.get().pluginId
            implementationClass = conventionPluginsPath + "CommonUiAndroidConventionPlugin"
        }
        register("dependencyGraphGenerator") {
            id = libs.plugins.dev.sergiobelda.gradle.dependency.graph.generator.get().pluginId
            implementationClass = conventionPluginsPath + "DependencyGraphGeneratorConventionPlugin"
        }

        val conventionPluginsLintPath = conventionPluginsPath + "lint."
        register("detekt") {
            id = libs.plugins.dev.sergiobelda.gradle.detekt.get().pluginId
            implementationClass = conventionPluginsLintPath + "DetektConventionPlugin"
        }
        register("spotless") {
            id =  libs.plugins.dev.sergiobelda.gradle.spotless.get().pluginId
            implementationClass = conventionPluginsLintPath + "SpotlessConventionPlugin"
        }
        register("lint") {
            id = libs.plugins.dev.sergiobelda.gradle.lint.get().pluginId
            implementationClass = conventionPluginsLintPath + "LintConventionPlugin"
        }
    }
}
