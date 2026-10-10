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
        androidResources.enable = true

        namespace = "dev.sergiobelda.todometer.common.resources"
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(deps.jetbrains.compose.ui)
            api(deps.jetbrains.compose.componentsResources)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "dev.sergiobelda.todometer.common.resources"
    generateResClass = always
}
