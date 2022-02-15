plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = Android.wearMinSdk
        targetSdk = Android.targetSdk
        versionCode = 4100101
        versionName = "wearos-1.0.0-alpha01"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    kotlinOptions {
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    implementation(projects.common)

    implementation(libs.androidx.activityCompose)

    implementation(libs.androidx.coreKtx)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.ui.toolingPreview)

    implementation(libs.wear.wear)
    implementation(libs.wear.compose.foundation)
    implementation(libs.wear.compose.material)
    implementation(libs.wear.compose.navigation)
    implementation(libs.wear.input)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.google.playServicesWearable)
}
