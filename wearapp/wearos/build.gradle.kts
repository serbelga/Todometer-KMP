plugins {
    alias(deps.plugins.android.application)
    alias(deps.plugins.google.ksp)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

if (file("google-services.json").exists()) {
    apply(plugin = deps.plugins.google.firebase.crashlytics.get().pluginId)
    apply(plugin = deps.plugins.google.gms.services.get().pluginId)
}

android {
    namespace = "dev.sergiobelda.todometer.wearapp.wearos"
    compileSdk = deps.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = deps.versions.android.wear.minSdk.get().toInt()
        targetSdk = deps.versions.android.wear.targetSdk.get().toInt()

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
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(projects.commonAndroid.extensions)
    implementation(projects.commonAndroid.resources)
    implementation(projects.common.core)
    implementation(projects.common.designsystemResources)
    implementation(projects.common.ui)

    implementation(deps.androidx.activityCompose)
    implementation(deps.androidx.core.ktx)
    implementation(deps.androidx.core.splashscreen)
    implementation(deps.androidx.wear.compose.foundation)
    implementation(deps.androidx.wear.compose.material)
    implementation(deps.androidx.wear.compose.navigation)
    implementation(deps.androidx.wear.compose.uiTooling)
    implementation(deps.androidx.wear.input)
    implementation(deps.androidx.wear.toolingPreview)
    implementation(deps.androidx.wear.wear)

    implementation(deps.jetbrains.kotlinx.collections.immutable)

    implementation(deps.google.gms.playServicesWearable)

    implementation(project.dependencies.platform(deps.google.firebase.bom))
    implementation(deps.google.firebase.analytics)
    implementation(deps.google.firebase.crashlytics)

    implementation(libs.sergiobelda.navigationComposeExtendedWear)
    ksp(libs.sergiobelda.navigationComposeExtendedCompiler)

    implementation(libs.sergiobelda.fonament.presentation)
    implementation(libs.sergiobelda.fonament.presentationDiKoin)
}
