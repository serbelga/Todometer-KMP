plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("todometer.spotless")
}

version = "1.0"

repositories {
    google()
}

kotlin {
    android()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Common"
        homepage = "https://github.com/serbelga/ToDometer_Kotlin_Multiplatform"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "common"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.koin.core)
                api(libs.koin.test)

                api(projects.commonDomain)
                implementation(projects.commonData)
                implementation(projects.commonNetwork)
                implementation(projects.commonPreferences)

                implementation(libs.kotlin.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.coroutinesTest)
                implementation(libs.mockk.common)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.androidx.appcompat)
                api(libs.androidx.coreKtx)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.mockk.mockk)
            }
        }
        val desktopMain by getting
        val desktopTest by getting {
            dependencies {
                implementation(libs.mockk.mockk)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
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
    }
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
    }
    namespace = "dev.sergiobelda.todometer.common"
}
