plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    id("todometer.common.library.android")
    id("todometer.dependency-graph-generator")
    id("todometer.spotless")
    id("todometer.common.generateImages")
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                implementation(compose.animationGraphics)
            }
        }
        val androidUnitTest by getting
        val desktopMain by getting
        val desktopTest by getting
        val iosMain by creating
        val iosTest by creating

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
