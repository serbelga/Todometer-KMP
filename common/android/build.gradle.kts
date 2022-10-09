plugins {
    id("com.android.library")
    kotlin("android")
    id("todometer.spotless")
}

android {
    namespace = "dev.sergiobelda.todometer.common.android.resources"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
}

dependencies {
    implementation(libs.androidx.navigation.runtimeKtx)
}
