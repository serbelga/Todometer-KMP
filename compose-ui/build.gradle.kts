import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "0.4.0-build173"
    id("com.android.library")
}

group = "com.sergiobelda.todometer.compose"
version = "1.0"

repositories {
    google()
}

// TODO Remove this block when https://youtrack.jetbrains.com/issue/KT-43944 resolved
android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
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
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
    }
}
