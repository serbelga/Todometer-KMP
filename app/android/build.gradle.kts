plugins {
    alias(deps.plugins.android.application)
    alias(deps.plugins.androidx.baselineprofile)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.sergiobelda.convention.lint)
    alias(libs.plugins.sergiobelda.todometer.dependencyGraphGenerator)
}

if (file("google-services.json").exists()) {
    apply(plugin = deps.plugins.google.firebase.crashlytics.get().pluginId)
    apply(plugin = deps.plugins.google.firebase.perf.get().pluginId)
    apply(plugin = deps.plugins.google.gms.services.get().pluginId)
}

android {
    namespace = "dev.sergiobelda.todometer.app.android"
    compileSdk = deps.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = deps.versions.android.minSdk.get().toInt()
        targetSdk = deps.versions.android.targetSdk.get().toInt()

        versionCode = 1298401
        versionName = "android-2.9.8"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // baselineProfile.automaticGenerationDuringBuild = true
        }
        debug {
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
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(projects.app.shared)
    implementation(projects.commonAndroid.resources)

    implementation(deps.androidx.glance.appWidget)
    implementation(deps.androidx.glance.glance)
    implementation(deps.androidx.glance.material3)
    implementation(deps.androidx.work.runtime)

    implementation(deps.google.gms.playServicesOssLicenses)

    implementation(project.dependencies.platform(deps.google.firebase.bom))
    implementation(deps.google.firebase.analytics)
    implementation(deps.google.firebase.crashlytics)
    implementation(deps.google.firebase.perf)

    baselineProfile(projects.macrobenchmark)
    implementation(deps.androidx.profileinstaller)
    "demoImplementation"(projects.commonAndroid.demoDatabase)
}

baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false

    mergeIntoMain = true
}
