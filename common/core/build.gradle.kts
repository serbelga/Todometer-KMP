plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    id("dev.sergiobelda.gradle.lint")
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
            implementation(projects.common.ui)
            implementation(projects.common.data)
            implementation(projects.common.database)
            implementation(projects.common.domain)
            implementation(projects.common.preferences)

            api(project.dependencies.platform(libs.koin.bom))
            api(libs.koin.compose)
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
    namespace = "dev.sergiobelda.todometer.common.core"
}
