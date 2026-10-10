plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(deps.plugins.sergiobelda.convention.lint)
    alias(libs.plugins.sergiobelda.todometer.common.library.android)
    alias(libs.plugins.sergiobelda.todometer.dependencyGraphGenerator)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.app.common.designsystem"
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.common.designsystemResources)

            implementation(deps.jetbrains.compose.foundation)
            implementation(deps.jetbrains.compose.material3)
            implementation(deps.jetbrains.compose.runtime)
            implementation(deps.jetbrains.compose.ui)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
