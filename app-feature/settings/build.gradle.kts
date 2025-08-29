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
            implementation(projects.common.domain)
            implementation(projects.common.resources)
            implementation(projects.common.ui)
        }

        androidMain.dependencies {
            implementation(compose.animationGraphics)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            api(libs.kotlin.coroutinesSwing)
            api(compose.uiTooling)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.app.feature.settings"

    dependencies {
        implementation(compose.preview)
        debugImplementation(compose.uiTooling)
        debugImplementation(projects.appCommon.uiTooling)
        debugImplementation(projects.common.uiTooling)
    }
}
