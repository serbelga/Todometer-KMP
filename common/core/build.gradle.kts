plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.data)
            implementation(projects.common.database)
            implementation(projects.common.di)
            implementation(projects.common.domain)
            implementation(projects.common.ui)

            implementation(libs.sergiobelda.fonament.preferencesDiKoin)
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.core"
}
