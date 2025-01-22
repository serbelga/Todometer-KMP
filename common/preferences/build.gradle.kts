plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.dev.sergiobelda.gradle.lint)
    id("dev.sergiobelda.gradle.common.library.android")
    id("dev.sergiobelda.gradle.dependency-graph-generator")
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.kotlin.coroutinesCore)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.preferences"
}
