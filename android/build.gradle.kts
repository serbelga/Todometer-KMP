@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.androidApplication)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlytics)
    kotlin("android")
    kotlin("kapt")
    id("todometer.spotless")
    id("todometer.dependency-graph-generator")
}

android {
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = libs.versions.androidMinSdk.get().toInt()

        versionCode = 1240201
        versionName = "android-2.4.0-beta01"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            extra["enableCrashlytics"] = false
        }
        lint {
            abortOnError = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
    namespace = "dev.sergiobelda.todometer"
}

dependencies {
    implementation(projects.common.android)
    implementation(projects.common.composeUi)
    implementation(projects.common.composeUiDesignsystem)
    implementation(projects.common.core)
    implementation(projects.common.domain)
    implementation(projects.common.navigation)
    implementation(projects.common.resources)
    implementation(projects.common.ui)

    implementation(compose.animationGraphics)

    implementation(libs.androidx.activityCompose)

    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodelCompose)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.splashscreen)

    implementation(libs.material)

    implementation(libs.timber)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.core)

    implementation(libs.accompanist.systemUiController)

    implementation(libs.androidx.glance.appWidget)
    implementation(libs.androidx.glance.glance)

    implementation(libs.google.playServicesOssLicenses)

    implementation(platform(libs.google.firebase.firebaseBom))
    implementation(libs.google.firebase.firebaseAnalyticsKtx)
    implementation(libs.google.firebase.firebaseCrashlyticsKtx)
}
