plugins {
    alias(deps.plugins.android.library)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

android {
    namespace = "dev.sergiobelda.todometer.common.android.demo.database"
    compileSdk = deps.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = deps.versions.android.minSdk.get().toInt()
    }

    kotlin {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}
