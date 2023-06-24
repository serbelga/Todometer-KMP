plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    id("todometer.common.library.android")
    id("todometer.spotless")
}

android {
    namespace = "dev.sergiobelda.todometer.common.android"
}

dependencies {

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.appcompat)
}
