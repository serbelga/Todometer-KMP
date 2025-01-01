plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.sqlDelight)
    id("dev.sergiobelda.gradle.base")
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
        val commonMain by getting {
            dependencies {
                implementation(libs.sqldelight.coroutines)
                implementation(libs.sqldelight.primitiveAdapters)

                implementation(projects.common.domain)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.coroutinesTest)
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.androidDriver)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.sqldelight.jvmDriver)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(libs.sqldelight.jvmDriver)
            }
        }
        val iosMain by creating {
            dependencies {
                implementation(libs.sqldelight.nativeDriver)
            }
        }
        val iosTest by creating

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
