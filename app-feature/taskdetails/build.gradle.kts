plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.common.ui)
    alias(libs.plugins.sergiobelda.gradle.common.uiToolingPreview)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.app.feature.taskdetails"
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.appCommon.ui)
            implementation(projects.appCommon.uiTooling)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
