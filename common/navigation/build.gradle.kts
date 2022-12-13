@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
}

group = "dev.sergiobelda.todometer.common.navigation"
version = "1.0"

repositories {
    google()
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.navigation.runtimeKtx)
            }
        }
        val androidTest by getting
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val desktopTest by getting

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
    }
    namespace = "dev.sergiobelda.todometer.common.navigation"
}
