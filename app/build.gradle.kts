import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

if (file("google-services.json").exists()) {
    apply(plugin = libs.plugins.google.firebaseCrashlytics.get().pluginId)
    apply(plugin = libs.plugins.google.services.get().pluginId)
}

kotlin {
    androidTarget()
    jvm("desktop")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "app"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.appCommon.ui)
            implementation(projects.appFeature.about)
            implementation(projects.appFeature.addtask)
            implementation(projects.appFeature.addtasklist)
            implementation(projects.appFeature.edittask)
            implementation(projects.appFeature.edittasklist)
            implementation(projects.appFeature.home)
            implementation(projects.appFeature.settings)
            implementation(projects.appFeature.taskdetails)

            implementation(projects.common.core)
            implementation(projects.common.ui)

            implementation(libs.sergiobelda.fonament.diKoin)
        }
        androidMain.dependencies {
            implementation(projects.common.android)

            implementation(libs.androidx.activityCompose)
            implementation(libs.androidx.glance.appWidget)
            implementation(libs.androidx.glance.glance)
            implementation(libs.androidx.glance.material3)
            implementation(libs.androidx.splashscreen)

            implementation(libs.google.playServicesOssLicenses)

            implementation(project.dependencies.platform(libs.google.firebase.firebaseBom))
            implementation(libs.google.firebase.firebaseAnalyticsKtx)
            implementation(libs.google.firebase.firebaseCrashlyticsKtx)

            // Workaround to avoid "Missing classes detected while running R8" using kotlinx-datetime library
            // TODO: Remove when issue https://github.com/Kotlin/kotlinx-datetime/issues/297 is resolved
            compileOnly(libs.jetbrains.kotlin.serialization.json)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.app"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.todometer"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        versionCode = 1292401
        versionName = "android-2.9.2"
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
    flavorDimensions += "version"
    productFlavors {
        /*create("prod") {
            dimension = "version"
            applicationIdSuffix = ".prod"
            versionNameSuffix = "-prod"
        }*/
        create("uiTest") {
            dimension = "version"
            applicationIdSuffix = ".uitest"
            versionNameSuffix = "-uitest"
        }
    }
    sourceSets {
        getByName("uiTest") {
            manifest.srcFile("src/androidUiTest/AndroidManifest.xml")
        }
    }
    kotlin {
        jvmToolchain(17)
    }

    dependencies {
        baselineProfile(projects.macrobenchmark)

        "uiTestImplementation"(projects.common.dataTest)

        implementation(libs.androidx.profileinstaller)
    }
}

compose.desktop {
    application {
        mainClass = "dev.sergiobelda.todometer.app.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            modules(
                "java.instrument",
                "java.management",
                "java.sql",
                "jdk.unsupported",
                "java.naming"
            )
            packageName = "Todometer"
            packageVersion = "1.1.0"
            copyright = "© 2023 Sergio Belda. Licensed under the Apache License."
            licenseFile.set(project.file("../LICENSE"))

            macOS {
                iconFile.set(project.file("./launcher_icons/macos_icon.icns"))
            }
            windows {
                iconFile.set(project.file("./launcher_icons/windows_icon.ico"))
            }
            linux {
                iconFile.set(project.file("./launcher_icons/linux_icon.png"))
            }
        }
    }
}
