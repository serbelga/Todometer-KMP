plugins {
    alias(deps.plugins.android.library)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

android {
    namespace = "dev.sergiobelda.todometer.common.android.demo.database"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }

    kotlin {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}
