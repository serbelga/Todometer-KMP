plugins {
    kotlin("multiplatform")
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelight)
    id("todometer.spotless")
}

version = "1.0"

kotlin {
    android()
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
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation(libs.sqldelight.nativeDriver)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
    }
    namespace = "dev.sergiobelda.todometer.common.database"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("TodometerDatabase") {
            packageName.set("dev.sergiobelda.todometer.common.database")
        }
    }
}
