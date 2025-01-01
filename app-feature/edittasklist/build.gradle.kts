plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    id("dev.sergiobelda.gradle.base")
    id("dev.sergiobelda.gradle.common.library.android")
    id("dev.sergiobelda.gradle.common.ui")
    id("dev.sergiobelda.gradle.dependency-graph-generator")
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
                implementation(projects.common.resources)
                implementation(projects.common.ui)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)
            }
        }
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                implementation(compose.animationGraphics)
                api(compose.uiTooling)
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
    namespace = "dev.sergiobelda.todometer.app.feature.edittasklist"
}
