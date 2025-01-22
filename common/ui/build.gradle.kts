plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
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
            implementation(projects.common.domain)

            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(libs.jetbrains.navigation.compose)
            api(libs.sergiobelda.navigationComposeExtended)
            api(libs.sergiobelda.navigationComposeExtendedAnnotation)

            implementation(libs.kotlin.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.coroutinesTest)
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(compose.uiTooling)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.ui"
}
