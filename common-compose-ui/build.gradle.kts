import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeMultiplatform)
    id("com.android.library")
    id("todometer.spotless")
}

group = "dev.sergiobelda.todometer.common.compose.ui"
version = "1.0"

repositories {
    google()
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                // TODO: Remove material dependency when everything depend on material3.
                api(compose.material)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.ui)
                api(compose.uiTooling)
                implementation(projects.common)

                api(libs.kotlin.coroutinesSwing)

                implementation(libs.kotlin.datetime)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api(libs.androidx.appcompat)
                api(libs.androidx.coreKtx)
                // TODO: Remove this usage when compose.material3 reaches Material3 Compose 1.0.0-beta01.
                api(libs.androidx.compose.material3)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(libs.junit)
            }
        }
        val desktopMain by getting
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
    namespace = "dev.sergiobelda.todometer.common.compose.ui"
}
