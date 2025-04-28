import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    alias(libs.plugins.android.test)
    alias(libs.plugins.androidx.baselineprofile)
    kotlin("android")
}

android {
    namespace = "dev.sergiobelda.todometer.app.benchmark"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    kotlin {
        jvmToolchain(17)
    }

    defaultConfig {
        minSdk = libs.versions.androidBaselineProfileMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Uncomment this line if you want to launch the benchmark on an emulator.
        // Is NOT recommend using the Android emulator for regular benchmarking, because the performance
        // is not representative of a real device.
        // Note: Avoid pushing this line uncommented to main branch.
        // testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
    }

    experimentalProperties["android.experimental.self-instrumenting"] = true

    targetProjectPath = ":app"

    // This code creates the gradle managed device (GMD) used to generate baseline profiles.
    testOptions.managedDevices.allDevices {
        create<ManagedVirtualDevice>("pixel6Api31") {
            device = "Pixel 6"
            apiLevel = 31
            systemImageSource = "aosp"
        }
    }
}

dependencies {
    implementation(libs.androidx.benchmark.macro.junit4)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.test.uiautomator)
}

baselineProfile {
    managedDevices += "pixel6Api31"
    useConnectedDevices = false
}
