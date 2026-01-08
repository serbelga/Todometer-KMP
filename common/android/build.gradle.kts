plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

android {
    namespace = "dev.sergiobelda.todometer.common.android"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.jetbrains.kotlin.coroutines.guava)
}
