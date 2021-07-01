plugins {
    id("org.jetbrains.compose") version Versions.composeDesktop
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
    implementation(project(":compose-ui"))

    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(Libs.AndroidX.Compose.materialIconsExtended)
    implementation(Libs.AndroidX.Compose.runtimeLiveData)
    implementation(Libs.AndroidX.Compose.uiTooling)
    implementation(Libs.AndroidX.Navigation.compose)
    implementation(Libs.AndroidX.Lifecycle.runtime)
    implementation(Libs.AndroidX.Lifecycle.viewModel)
    implementation(Libs.AndroidX.Lifecycle.liveData)
    implementation(Libs.Google.Material.materialComponents)

    implementation(Libs.timber)

    implementation(Libs.Koin.android)
    implementation(Libs.Koin.compose)
    implementation(Libs.Koin.core)
}

android {
    compileSdk = 30
    defaultConfig {
        applicationId = "com.sergiobelda.todometer.android"
        minSdk = 24
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
