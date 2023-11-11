plugins {
    kotlin("multiplatform")
    alias(libs.plugins.androidLibrary)
    id("todometer.common.library.android")
    id("todometer.dependency-graph-generator")
    id("todometer.spotless")
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
                api(libs.koin.core)
                api(libs.koin.test)

                implementation(projects.common.ui)
                implementation(projects.common.data)
                implementation(projects.common.database)
                implementation(projects.common.domain)
                implementation(projects.common.preferences)

                implementation(libs.koin.core)
                implementation(libs.koin.test)
            }
        }
        val commonTest by getting
        val androidMain by getting
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
