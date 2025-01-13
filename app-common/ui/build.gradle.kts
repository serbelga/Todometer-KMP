plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    id("dev.sergiobelda.gradle.lint")
    id("dev.sergiobelda.gradle.common.library.android")
    id("dev.sergiobelda.gradle.dependency-graph-generator")
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
            implementation(projects.common.domain)
            implementation(projects.common.resources)
            implementation(projects.common.ui)

            implementation(libs.kotlin.datetime)
            implementation(libs.sergiobelda.pigment)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.coroutinesTest)
            implementation(libs.mockk.common)
            implementation(kotlin("test"))
        }
        val desktopMain by getting
        desktopMain.dependencies {
            api(libs.kotlin.coroutinesSwing)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.app.common.ui"
}
