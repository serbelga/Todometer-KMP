plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer.wear"
        minSdk = 25
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

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

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("com.google.android.gms:play-services-wearable:17.1.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.wear:wear:1.2.0")
    implementation(project(":common"))
}