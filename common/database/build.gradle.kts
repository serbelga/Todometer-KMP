plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.sqldelight.coroutines)
            implementation(libs.sqldelight.primitiveAdapters)

            implementation(projects.common.domain)
        }
        commonTest.dependencies {
            implementation(libs.jetbrains.kotlin.coroutines.test)
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.androidDriver)
        }
        androidUnitTest.dependencies {
            implementation(libs.sqldelight.jvmDriver)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.sqldelight.jvmDriver)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.nativeDriver)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.database"

    buildTypes {
        create("benchmark") {
            signingConfig = getByName("debug").signingConfig
            matchingFallbacks += listOf("release")
        }
    }
}

sqldelight {
    databases {
        create("TodometerDatabase") {
            packageName.set("dev.sergiobelda.todometer.common.database")
        }
    }
}
