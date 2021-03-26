plugins {
    id("org.jetbrains.compose") version "0.4.0-build173"
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

group = "com.sergiobelda.todometer"
version = "1.0"

repositories {
    google()
}

dependencies {
    implementation(project(":common"))

    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(Libs.AndroidX.Compose.materialIconsExtended)
    implementation(Libs.AndroidX.Compose.runtimeLiveData)
    implementation(Libs.AndroidX.Compose.uiTooling)
    implementation(Libs.AndroidX.Navigation.compose)
    implementation(Libs.AndroidX.Lifecycle.runtime)
    implementation(Libs.AndroidX.Lifecycle.viewModel)
    implementation(Libs.AndroidX.Lifecycle.liveData)
    implementation(Libs.Google.Material.materialComponents)

    // Room dependencies
    implementation(Libs.AndroidX.Room.roomKtx)
    implementation(Libs.AndroidX.Room.roomRuntime)
    // Required: Room compiler (avoid RuntimeException - cannot find implementation for database)
    kapt(Libs.AndroidX.Room.roomCompiler)
    androidTestImplementation(Libs.AndroidX.Room.roomTesting)

    // Dagger Hilt
    implementation(Libs.Google.Dagger.hilt)
    kapt(Libs.Google.Dagger.hiltCompiler)
    androidTestImplementation(Libs.Google.Dagger.hiltTesting)
    kaptAndroidTest(Libs.Google.Dagger.hiltCompiler)
    testImplementation(Libs.Google.Dagger.hiltTesting)
    kaptTest(Libs.Google.Dagger.hiltCompiler)

    implementation(Libs.timber)
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.sergiobelda.todometer.android"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}