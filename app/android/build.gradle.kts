plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

if (file("google-services.json").exists()) {
    apply(plugin = libs.plugins.google.firebaseCrashlytics.get().pluginId)
    apply(plugin = libs.plugins.google.firebasePerf.get().pluginId)
    apply(plugin = libs.plugins.google.services.get().pluginId)
}

android {
    namespace = "dev.sergiobelda.todometer.app.android"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        versionCode = 1295401
        versionName = "android-2.9.5"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            baselineProfile.automaticGenerationDuringBuild = true
        }
        getByName("debug") {
            extra["enableCrashlytics"] = false
        }
        lint {
            abortOnError = false
        }
    }
    flavorDimensions += "version"
    productFlavors {
        create("prod") {
            dimension = "version"
            isDefault = true
            versionNameSuffix = "-prod"
        }
        create("demo") {
            dimension = "version"
            versionNameSuffix = "-demo"
        }
    }
    sourceSets {
        getByName("prod") {
            manifest.srcFile("src/prod/AndroidManifest.xml")
        }
        getByName("demo") {
            manifest.srcFile("src/demo/AndroidManifest.xml")
        }
    }

    kotlin {
        jvmToolchain(21)
    }
}

dependencies {
    implementation(projects.app.shared)
    implementation(projects.commonAndroid.resources)

    implementation(libs.androidx.glance.appWidget)
    implementation(libs.androidx.glance.glance)
    implementation(libs.androidx.glance.material3)

    implementation(libs.google.playServicesOssLicenses)

    implementation(project.dependencies.platform(libs.google.firebase.firebaseBom))
    implementation(libs.google.firebase.firebaseAnalytics)
    implementation(libs.google.firebase.firebaseCrashlytics)
    implementation(libs.google.firebase.firebasePerf)

    baselineProfile(projects.macrobenchmark)
    implementation(libs.androidx.profileinstaller)
    "demoImplementation"(projects.commonAndroid.demoDatabase)
}

baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false

    mergeIntoMain = true
}
