plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

version = "1.0"

kotlin {
    android()
    jvm("desktop")

    val iosTarget: (String, org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit) -> org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Common data"
        homepage = "https://github.com/serbelga/ToDometer_Kotlin_Multiplatform"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "common-data"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.json)
                implementation(libs.ktor.client.serialization)
                implementation(libs.sqldelight.coroutines)

                implementation(projects.commonDomain)
                implementation(projects.commonPreferences)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.coroutinesTest)
                implementation(libs.mockk.common)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.sqldelight.androidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.sqldelight.jvmDriver)
                implementation(libs.mockk.mockk)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.ktor.client.apache)
                implementation(libs.sqldelight.jvmDriver)
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(libs.mockk.mockk)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.ios)
                implementation(libs.sqldelight.nativeDriver)
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
    }
}

sqldelight {
    database("TodometerDatabase") {
        packageName = "dev.sergiobelda.todometer.common.data.database"
    }
}
