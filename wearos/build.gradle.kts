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
        versionCode = 1
        versionName = "1.0.0-dev01"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    kotlinOptions {
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    implementation(project(":common"))

    implementation(Libs.AndroidX.coreKtx)

    implementation(Libs.AndroidX.Activity.activityCompose)

    with(Libs.AndroidX.Compose) {
        implementation(foundation)
        implementation(materialIconsExtended)
        implementation(uiToolingPreview)
    }

    with(Libs.AndroidX.Wear) {
        implementation(wear)
        implementation(composeFoundation)
        implementation(composeMaterial)
        implementation(composeNavigation)
    }

    with(Libs.Koin) {
        implementation(android)
        implementation(compose)
    }
    
    implementation(Libs.Google.Services.wearable)
}
