plugins {
    id("org.jetbrains.compose") version Versions.composeMultiplatform
    id("com.android.application")
    id("com.google.android.gms.oss-licenses-plugin")
    kotlin("android")
    kotlin("kapt")
}

group = "dev.sergiobelda.todometer"
version = "1.0"

repositories {
    google()
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

    with(Libs.AndroidX.Glance) {
        implementation(glance)
        implementation(glanceAppWidget)
    }

    implementation(Libs.Google.Services.ossLicenses)
}

android {
    compileSdk = Android.compileSdk
    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = 2
        versionName = "2.0.0-dev05"
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
