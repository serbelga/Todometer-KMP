plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.dev.sergiobelda.gradle.lint)
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
            implementation(libs.sqldelight.coroutines)
            implementation(libs.sqldelight.primitiveAdapters)

            implementation(projects.common.domain)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.coroutinesTest)
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
