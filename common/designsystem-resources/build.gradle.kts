plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.composeVectorize)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin {
    androidLibrary {
        androidResources.enable = true

        namespace = "dev.sergiobelda.todometer.common.designsystem.resources"

        lint {
            abortOnError = false
        }
    }
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.sergiobelda.composeVectorize.core)
        }
        androidMain.dependencies {
            implementation(libs.jetbrains.compose.animationGraphics)
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
