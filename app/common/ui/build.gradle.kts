plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    id("todometer.common.library.android")
    id("todometer.dependency-graph-generator")
    id("todometer.spotless")
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
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
                api(compose.ui)

                implementation(projects.app.common.designsystem)
                implementation(projects.common.domain)
                implementation(projects.common.navigation)
                implementation(projects.common.resources)
                implementation(projects.common.viewmodel)
                implementation(projects.common.ui)

                implementation(libs.kotlin.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.coroutinesTest)
                implementation(libs.mockk.common)
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(compose.animationGraphics)
                implementation(compose.uiTooling)
                implementation(libs.androidx.navigation.compose)
            }
        }
        val androidUnitTest by getting
        val desktopMain by getting {
            dependencies {
                api(libs.kotlin.coroutinesSwing)
                api(compose.uiTooling)
            }
        }
        val desktopTest by getting
        val iosMain by creating
        val iosTest by creating

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.app.common.ui"
}
