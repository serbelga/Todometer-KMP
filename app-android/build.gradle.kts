plugins {
    alias(libs.plugins.androidApplication)
    kotlin("android")
    alias(libs.plugins.composeMultiplatform)
    id("com.google.android.gms.oss-licenses-plugin")
    id("dev.sergiobelda.gradle.spotless")
    id("dev.sergiobelda.gradle.dependency-graph-generator")
}

if (file("google-services.json").exists()) {
    apply(plugin = libs.plugins.googleServices.get().pluginId)
    apply(plugin = libs.plugins.firebaseCrashlytics.get().pluginId)
}

android {
    namespace = "dev.sergiobelda.todometer.app.android"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        versionCode = 1281400
        versionName = "android-2.8.1"
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
    kotlin {
        jvmToolchain(17)
    }
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(projects.app.common.designsystem)
    implementation(projects.app.common.ui)
    implementation(projects.app.feature.about)
    implementation(projects.app.feature.addtask)
    implementation(projects.app.feature.addtasklist)
    implementation(projects.app.feature.edittask)
    implementation(projects.app.feature.edittasklist)
    implementation(projects.app.feature.home)
    implementation(projects.app.feature.settings)
    implementation(projects.app.feature.taskdetails)
    implementation(projects.common.android)
    implementation(projects.common.core)
    implementation(projects.common.domain)
    implementation(projects.common.navigation)
    implementation(projects.common.resources)
    implementation(projects.common.ui)

    implementation(libs.androidx.activityCompose)

    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    // TODO: Remove when update Compose Multiplatform to 1.7.0
    implementation("androidx.compose.ui:ui:1.7.0-alpha05")

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.splashscreen)

    implementation(libs.timber)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.androidx.glance.appWidget)
    implementation(libs.androidx.glance.glance)
    implementation(libs.androidx.glance.material3)

    implementation(libs.google.playServicesOssLicenses)

    implementation(project.dependencies.platform(libs.google.firebase.firebaseBom))
    implementation(libs.google.firebase.firebaseAnalyticsKtx)
    implementation(libs.google.firebase.firebaseCrashlyticsKtx)

    // Workaround to avoid "Missing classes detected while running R8" using kotlinx-datetime library
    // TODO: Remove when issue https://github.com/Kotlin/kotlinx-datetime/issues/297 is resolved
    compileOnly(libs.kotlin.serialization.json)
}
