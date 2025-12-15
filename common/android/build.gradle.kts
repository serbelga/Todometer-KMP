plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

android {
    namespace = "dev.sergiobelda.todometer.common.android"
}

dependencies {
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.jetbrains.kotlin.coroutines.guava)
}
