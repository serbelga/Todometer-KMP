plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
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
            implementation(projects.common.database)
            implementation(projects.common.preferences)

            implementation(libs.kotlin.coroutinesCore)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.coroutinesTest)
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
