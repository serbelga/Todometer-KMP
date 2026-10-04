plugins {
    alias(deps.plugins.android.library)
    alias(deps.plugins.sergiobelda.convention.lint)
    alias(libs.plugins.sergiobelda.todometer.dependencyGraphGenerator)
}

android {
    namespace = "dev.sergiobelda.todometer.common.android.resources"
    compileSdk = deps.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = deps.versions.android.minSdk.get().toInt()
    }

    kotlin {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(deps.androidx.core.ktx)
}
