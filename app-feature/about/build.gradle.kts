plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.common.ui)
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
            implementation(projects.appCommon.designsystem)
            implementation(projects.appCommon.ui)
            implementation(projects.common.resources)
            implementation(projects.common.ui)
        }
        androidMain.dependencies {
            implementation(libs.jetbrains.compose.animationGraphics)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            api(libs.jetbrains.compose.uiTooling)
            api(libs.jetbrains.kotlin.coroutines.swing)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.app.feature.about"

    dependencies {
        debugImplementation(projects.appCommon.uiTooling)
        debugImplementation(projects.common.uiTooling)
        debugImplementation(libs.jetbrains.compose.uiTooling)
        implementation(libs.jetbrains.compose.uiToolingPreview)
    }
}
