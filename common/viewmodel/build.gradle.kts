plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
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
                implementation(libs.kotlin.coroutinesCore)
                implementation(libs.kotlin.datetime)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.coreKtx)
                implementation(libs.androidx.lifecycle.viewmodel)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.mockk.mockk)
            }
        }
        val desktopMain by getting
        val desktopTest by getting {
            dependencies {
                implementation(libs.mockk.mockk)
            }
        }
        val iosMain by creating
        val iosTest by creating
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.viewmodel"
}
