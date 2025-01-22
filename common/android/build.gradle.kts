plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
    alias(libs.plugins.dev.sergiobelda.gradle.lint)
    id("dev.sergiobelda.gradle.common.library.android")
    alias(libs.plugins.dev.sergiobelda.gradle.dependency.graph.generator)
}

android {
    namespace = "dev.sergiobelda.todometer.common.android"
}

dependencies {

    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.appcompat)
}
