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
            api(project.dependencies.platform(libs.koin.bom))
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
            api(libs.koin.core)
            api(libs.koin.test)
        }
        androidMain.dependencies {
            api(libs.koin.android)
            api(libs.koin.androidXCompose)
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.di"
}
