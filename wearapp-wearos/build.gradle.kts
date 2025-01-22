plugins {
    alias(libs.plugins.android.application)
    kotlin("android")
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

if (file("google-services.json").exists()) {
    apply(plugin = libs.plugins.google.firebaseCrashlytics.get().pluginId)
    apply(plugin = libs.plugins.google.services.get().pluginId)
}

android {
    namespace = "dev.sergiobelda.todometer.wearapp.wearos"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = libs.versions.androidWearMinSdk.get().toInt()
        targetSdk = libs.versions.androidWearTargetSdk.get().toInt()

        versionCode = 4170201
        versionName = "wearos-1.7.0-beta01"
    }

    buildFeatures {
        compose = true
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
            getByName("debug") {
                extra["enableCrashlytics"] = false
            }
        }
        lint {
            abortOnError = false
        }
    }

    kotlin {
        jvmToolchain(17)
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(projects.common.android)
    implementation(projects.common.core)
    implementation(projects.common.designsystemResources)
    implementation(projects.common.domain)
    implementation(projects.common.resources)
    implementation(projects.common.ui)

    implementation(compose.foundation)
    implementation(compose.uiTooling)

    implementation(libs.androidx.activityCompose)

    implementation(libs.androidx.coreKtx)

    implementation(libs.androidx.splashscreen)

    implementation(libs.wear.compose.foundation)
    implementation(libs.wear.compose.material)
    implementation(libs.wear.compose.navigation)
    implementation(libs.wear.input)
    implementation(libs.wear.wear)

    implementation(libs.google.playServicesWearable)

    implementation(project.dependencies.platform(libs.google.firebase.firebaseBom))
    implementation(libs.google.firebase.firebaseAnalyticsKtx)
    implementation(libs.google.firebase.firebaseCrashlyticsKtx)

    implementation(libs.sergiobelda.navigationComposeExtendedWear)
    ksp(libs.sergiobelda.navigationComposeExtendedCompiler)

    implementation(libs.kotlin.collections.immutable)
}
