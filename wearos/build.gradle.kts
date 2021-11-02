plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer.wear"
        minSdk = Android.wearMinSdk
        targetSdk = Android.targetSdk
        versionCode = 1
        versionName = "1.0.0-dev01"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    with(Libs.AndroidX) {
        implementation(coreKtx)
    }

    with(Libs.AndroidX.Wear) {
        implementation(wear)
        implementation(composeFoundation)
        implementation(composeMaterial)
        implementation(composeNavigation)
    }

    with(Libs.Google.Services) {
        implementation(wearable)
    }

    implementation(project(":common"))
}
