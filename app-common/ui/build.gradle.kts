plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.common.ui)
            api(projects.appCommon.designsystem)

            implementation(libs.sergiobelda.pigment)
        }
        commonTest.dependencies {
            implementation(libs.jetbrains.kotlin.coroutines.test)
            implementation(libs.mockk.common)
            implementation(kotlin("test"))
        }
        val desktopMain by getting
        desktopMain.dependencies {
            api(libs.jetbrains.kotlin.coroutines.swing)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.app.common.ui"
}
