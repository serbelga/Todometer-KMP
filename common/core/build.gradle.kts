plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.common.core"
    }
    jvm()
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
