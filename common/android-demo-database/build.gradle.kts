plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

android {
    namespace = "dev.sergiobelda.todometer.common.android.demo.database"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    kotlin {
        jvmToolchain(17)
    }
}
