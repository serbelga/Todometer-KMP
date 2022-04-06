import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

version = "1.0"

repositories {
    google()
}

kotlin {
    android {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Common"
        homepage = "https://github.com/serbelga/ToDometer_Kotlin_Multiplatform"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "common"
        }
        podfile = project.file("../ios/Podfile")
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.koin.core)
                api(libs.koin.test)

                implementation(projects.commonData)
                api(projects.commonDomain)
                implementation(projects.commonPreferences)
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
        val iosMain by getting
        val iosTest by getting
    }
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
