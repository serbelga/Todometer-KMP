plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    id("dev.sergiobelda.gradle.common.library.android")
    id("dev.sergiobelda.gradle.dependency-graph-generator")
    id("dev.sergiobelda.gradle.spotless")
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.runtime)

                api(libs.jetbrains.navigation.compose)
            }
        }
        val commonTest by getting
        val androidMain by getting
        val androidUnitTest by getting
        val desktopMain by getting
        val desktopTest by getting
        val iosMain by creating
        val iosTest by creating

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.navigation"
}
