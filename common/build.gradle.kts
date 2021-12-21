import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
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
        frameworkName = "common"
        podfile = project.file("../ios/Podfile")
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Libs.Koin.core)
                api(Libs.Koin.test)
                implementation(Libs.Ktor.clientCore)
                implementation(Libs.Ktor.clientJson)
                implementation(Libs.Ktor.clientSerialization)
                implementation(Libs.SqlDelight.coroutines)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Libs.kotlinCoroutinesTest)
                implementation(Libs.MockK.mockkCommon)
            }
        }
        val androidMain by getting {
            dependencies {
                api(Libs.AndroidX.appCompat)
                api(Libs.AndroidX.coreKtx)
                implementation(Libs.AndroidX.DataStore.preferences)
                implementation(Libs.Ktor.clientAndroid)
                implementation(Libs.SqlDelight.androidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Libs.junit)
                implementation(Libs.SqlDelight.jvmDriver)
                implementation(Libs.MockK.mockk)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(Libs.Ktor.clientApache)
                implementation(Libs.SqlDelight.jvmDriver)
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(Libs.MockK.mockk)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Libs.Ktor.clientIos)
                implementation(Libs.SqlDelight.nativeDriver)
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdk = Android.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    database("TodometerDatabase") {
        packageName = "dev.sergiobelda.todometer"
    }
}
