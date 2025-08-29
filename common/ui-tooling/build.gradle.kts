plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
    kotlin("android")
}

android {
    namespace = "dev.sergiobelda.todometer.common.ui.tooling"
}

dependencies {
    implementation(compose.preview)
    implementation(libs.kotlin.datetime)
}
