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
    namespace = "dev.sergiobelda.todometer.app.common.ui.tooling"
}

dependencies {
    implementation(projects.appCommon.ui)
    implementation(projects.common.resources)

    implementation(libs.jetbrains.compose.ui)
    implementation(libs.jetbrains.compose.uiToolingPreview)
}
