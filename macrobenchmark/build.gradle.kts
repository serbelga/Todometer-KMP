import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    alias(deps.plugins.android.test)
    alias(deps.plugins.androidx.baselineprofile)
}

android {
    namespace = "dev.sergiobelda.todometer.app.benchmark"
    compileSdk = deps.versions.android.compileSdk.get().toInt()

    kotlin {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = deps.versions.android.baselineprofile.minSdk.get().toInt()
        targetSdk = deps.versions.android.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Uncomment this line if you want to launch the benchmark on an emulator.
        // Is NOT recommend using the Android emulator for regular benchmarking, because the performance
        // is not representative of a real device.
        // Note: Avoid pushing this line uncommented to main branch.
        // testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
    }

    flavorDimensions += "version"
    productFlavors {
        create("demo") { dimension = "version" }
        create("prod") { dimension = "version" }
    }

    experimentalProperties["android.experimental.self-instrumenting"] = true

    targetProjectPath = ":app:android"

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
    implementation(projects.common.ui)
    implementation(projects.appFeature.home)

    implementation(deps.androidx.benchmark.macroJunit4)
    implementation(deps.androidx.test.espresso.core)
    implementation(deps.androidx.test.ext.junit)
    implementation(deps.androidx.test.uiautomator)
}

baselineProfile {
    managedDevices += "pixel6Api31"
    useConnectedDevices = false
}
