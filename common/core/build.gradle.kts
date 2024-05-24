plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
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
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api(libs.koin.android)
                api(libs.koin.androidXCompose)
            }
        }
        val androidUnitTest by getting
        val desktopMain by getting
        val desktopTest by getting
        val iosMain by creating
        val iosTest by creating
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.core"
}
