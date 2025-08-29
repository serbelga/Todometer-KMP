plugins {
    alias(libs.plugins.android.library)
    kotlin("android")
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)

    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
}

android {
    namespace = "dev.sergiobelda.todometer.app.common.ui.tooling"
}

dependencies {
    implementation(projects.appCommon.ui)
    implementation(projects.common.resources)

    implementation(compose.ui)
    implementation(compose.preview)
}
