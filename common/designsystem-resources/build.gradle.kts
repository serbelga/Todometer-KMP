plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(deps.plugins.sergiobelda.convention.lint)
    alias(libs.plugins.sergiobelda.composeVectorize)
    alias(libs.plugins.sergiobelda.todometer.common.library.android)
    alias(libs.plugins.sergiobelda.todometer.dependencyGraphGenerator)
}

kotlin {
    android {
        androidResources.enable = true

        namespace = "dev.sergiobelda.todometer.common.designsystem.resources"

        lint {
            abortOnError = false
        }
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(deps.jetbrains.compose.componentsResources)
            implementation(deps.jetbrains.compose.material3)
            implementation(deps.jetbrains.compose.ui)
            implementation(libs.sergiobelda.composeVectorize.core)
        }
        androidMain.dependencies {
            implementation(deps.jetbrains.compose.animationGraphics)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

composeVectorize {
    packageName = "dev.sergiobelda.todometer.common.designsystem.resources.images"
}

// Workaround to be able to run macrobenchmark tests - Update compose-vectorize if necessary.
tasks["generateImages"].mustRunAfter("prepareAndroidMainArtProfile")

compose.resources {
    packageOfResClass = "dev.sergiobelda.todometer.common.designsystem.resources"
    generateResClass = always
}
