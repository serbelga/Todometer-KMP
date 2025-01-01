plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    id("dev.sergiobelda.gradle.base")
    id("dev.sergiobelda.gradle.common.library.android")
    id("dev.sergiobelda.gradle.dependency-graph-generator")
}

android {
    namespace = "dev.sergiobelda.todometer.common.android"
}

dependencies {

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.appcompat)
}
