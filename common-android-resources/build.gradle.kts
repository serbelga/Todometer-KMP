plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "dev.sergiobelda.todometer.common.android.resources"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}
