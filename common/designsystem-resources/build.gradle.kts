plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.composeVectorize)
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
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(libs.sergiobelda.composeVectorize.core)
        }
        androidMain.dependencies {
            implementation(compose.animationGraphics)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    sourceSets["main"].resources.srcDir("src/commonMain/resources")

    namespace = "dev.sergiobelda.todometer.common.designsystem.resources"

    lint {
        abortOnError = false
    }
}

composeVectorize {
    packageName = "dev.sergiobelda.todometer.common.designsystem.resources.images"
}
