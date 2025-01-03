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
        register("baseLibrary") {
            id = "dev.sergiobelda.gradle.base"
            implementationClass =
                "dev.sergiobelda.gradle.buildlogic.convention.BaseConventionPlugin"
        }
        register("commonLibraryAndroid") {
            id = "dev.sergiobelda.gradle.common.library.android"
            implementationClass =
                "dev.sergiobelda.gradle.buildlogic.convention.CommonLibraryAndroidConventionPlugin"
        }
        register("commonUi") {
            id = "dev.sergiobelda.gradle.common.ui"
            implementationClass =
                "dev.sergiobelda.gradle.buildlogic.convention.CommonUiAndroidConventionPlugin"
        }
        register("dependencyGraphGenerator") {
            id = "dev.sergiobelda.gradle.dependency-graph-generator"
            implementationClass =
                "dev.sergiobelda.gradle.buildlogic.convention.DependencyGraphGeneratorConventionPlugin"
        }
        register("spotless") {
            id = "dev.sergiobelda.gradle.spotless"
            implementationClass =
                "dev.sergiobelda.gradle.buildlogic.convention.SpotlessConventionPlugin"
        }
    }
}
