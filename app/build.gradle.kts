import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin {
    androidLibrary {
        namespace = "dev.sergiobelda.todometer.app"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()
        androidResources { enable = true }
    }
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
            api(projects.appCommon.ui)
            api(projects.common.core)

            implementation(projects.appFeature.about)
            implementation(projects.appFeature.addtask)
            implementation(projects.appFeature.addtasklist)
            implementation(projects.appFeature.edittask)
            implementation(projects.appFeature.edittasklist)
            implementation(projects.appFeature.home)
            implementation(projects.appFeature.settings)
            implementation(projects.appFeature.taskdetails)

            implementation(libs.sergiobelda.fonament.presentationDiKoin)
        }
        androidMain.dependencies {
            implementation(projects.common.android)

            implementation(libs.androidx.activityCompose)
            implementation(libs.androidx.splashscreen)

            implementation(libs.google.playServicesOssLicenses)
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
