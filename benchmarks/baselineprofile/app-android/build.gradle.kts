import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    alias(libs.plugins.androidTest)
    alias(libs.plugins.androidxBaselineprofile)
    alias(libs.plugins.kotlinAndroid)
    id("todometer.spotless")
}

android {
    namespace = "dev.sergiobelda.todometer.benchmarks.baselineprofile.app.android"

    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    kotlin {
        jvmToolchain(17)
    }

    defaultConfig {
        // Set minSdk to 28 to support BaselineProfileRule collect.
        minSdk = 28
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app-android"

    testOptions.managedDevices.devices {
        create<ManagedVirtualDevice>("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = 34
            systemImageSource = "google"
        }
    }
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    managedDevices += "pixel6Api34"
    useConnectedDevices = false
}

dependencies {
    implementation(libs.androidx.test.benchmarkMacroJunit4)
    implementation(libs.androidx.test.espressoCore)
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.test.uiautomator)
}
