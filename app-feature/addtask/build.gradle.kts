plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    id("dev.sergiobelda.gradle.common.library.android")
    id("dev.sergiobelda.gradle.dependency-graph-generator")
    id("dev.sergiobelda.gradle.spotless")
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
                implementation(projects.appCommon.designsystem)
                implementation(projects.appCommon.ui)
                implementation(projects.common.domain)
                implementation(projects.common.navigation)
                implementation(projects.common.resources)
                implementation(projects.common.ui)
                implementation(projects.common.viewmodel)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                implementation(compose.animationGraphics)
                api(compose.uiTooling)

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
    namespace = "dev.sergiobelda.todometer.app.feature.addtask"
}
