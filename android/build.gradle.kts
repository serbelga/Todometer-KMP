plugins {
    id("org.jetbrains.compose") version Versions.composeMultiplatform
    id("com.android.application")
    id("com.google.android.gms.oss-licenses-plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = 1200101
        versionName = "android-2.0.0-alpha01"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        lint {
            abortOnError = false
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

dependencies {
    implementation(project(":common"))
    implementation(project(":common-compose-ui"))

    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(Libs.AndroidX.Compose.runtimeLiveData)
    implementation(Libs.AndroidX.Navigation.compose)

    with(Libs.AndroidX.Lifecycle) {
        implementation(runtime)
        implementation(viewModel)
        implementation(liveData)
    }

    implementation(Libs.Google.Material.materialComponents)

    implementation(Libs.timber)

    with(Libs.Koin) {
        implementation(android)
        implementation(compose)
        implementation(core)
    }

    implementation(Libs.Google.accompanistSystemUiController)

    implementation(Libs.Google.Services.ossLicenses)
}
