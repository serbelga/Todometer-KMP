plugins {
    kotlin("multiplatform")
    alias(libs.plugins.androidLibrary)
    id("todometer.common.library.android")
    id("todometer.dependency-graph-generator")
    id("todometer.spotless")
}

version = "1.0"

kotlin {
    android()
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
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.core"
}
