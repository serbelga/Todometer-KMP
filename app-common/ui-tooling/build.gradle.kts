plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.app.common.ui.tooling"
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.common.uiTooling)

            implementation(projects.appCommon.ui)
            implementation(projects.common.resources)
        }
    }
}
