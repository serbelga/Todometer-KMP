import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

group = "com.sergiobelda.todometer.common"
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
    ios {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
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
        val commonTest by getting
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.3.0")
                api("androidx.core:core-ktx:1.5.0")
                implementation(Libs.AndroidX.DataStore.preferences)
                implementation(Libs.Ktor.clientAndroid)
                implementation(Libs.SqlDelight.androidDriver)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(Libs.junit)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(Libs.Ktor.clientApache)
                implementation(Libs.SqlDelight.jvmDriver)
            }
        }
        val desktopTest by getting
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
    compileSdk = 30
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 30
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)

sqldelight {
    database("TodometerDatabase") {
        packageName = "com.sergiobelda.todometer"
    }
}
