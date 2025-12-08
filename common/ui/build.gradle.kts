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
            api(projects.common.di)
            api(projects.common.domain)
            api(projects.common.resources)
            api(projects.common.uiTooling)

            api(libs.jetbrains.androidx.navigation.compose)
            api(libs.jetbrains.compose.foundation)
            api(libs.jetbrains.compose.material3)
            api(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.compose.ui)
            api(libs.jetbrains.kotlin.collections.immutable)
            api(libs.jetbrains.kotlin.datetime)

            api(libs.sergiobelda.navigationComposeExtended)
            api(libs.sergiobelda.navigationComposeExtendedAnnotation)
        }
        commonTest.dependencies {
            implementation(libs.jetbrains.kotlin.coroutines.test)
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            api(libs.jetbrains.compose.animationGraphics)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.ui"
}
