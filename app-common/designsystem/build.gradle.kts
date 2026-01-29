plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin {
    androidLibrary {
        namespace = "dev.sergiobelda.todometer.app.common.designsystem"
    }
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.common.designsystemResources)

            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.ui)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
