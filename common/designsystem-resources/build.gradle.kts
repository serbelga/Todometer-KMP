plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeVectorize)
    alias(libs.plugins.dev.sergiobelda.gradle.lint)
    alias(libs.plugins.dev.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.dev.sergiobelda.gradle.dependency.graph.generator)
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
