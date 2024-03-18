plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.todometer.buildlogic"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
    implementation(libs.dependencyGraphGenerator)
}

gradlePlugin {
    plugins {
        register("commonLibraryAndroid") {
            id = "dev.sergiobelda.gradle.common.library.android"
            implementationClass =
                "dev.sergiobelda.gradle.buildlogic.convention.CommonLibraryAndroidConventionPlugin"
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
