import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.composeDesktop
    id("com.android.library")
}

group = "dev.sergiobelda.todometer.common.compose.ui"
version = "1.0"

repositories {
    google()
}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(compose.ui)
                api(compose.uiTooling)
                implementation(project(":common"))
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api(Libs.AndroidX.appCompat)
                api(Libs.AndroidX.coreKtx)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Libs.junit)
            }
        }
        val desktopMain by getting
        val desktopTest by getting
    }
}

android {
    compileSdk = Android.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
}
