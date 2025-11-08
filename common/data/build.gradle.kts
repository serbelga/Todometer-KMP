plugins {
    alias(libs.plugins.android.library)
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
            implementation(projects.common.domain)
            implementation(projects.common.database)
            implementation(projects.common.preferences)

            implementation(libs.jetbrains.kotlin.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.jetbrains.kotlin.coroutines.test)
            implementation(libs.mockk.common)
            implementation(kotlin("test"))
        }
        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.mockk.mockk)
        }
        val desktopTest by getting
        desktopTest.dependencies {
            implementation(libs.mockk.mockk)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.data"
}
