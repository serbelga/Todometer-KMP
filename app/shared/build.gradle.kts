plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.app"
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        minSdk = libs.versions.androidMinSdk.get().toInt()
        androidResources { enable = true }
    }
    jvm()
    listOf(
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
            implementation(projects.commonAndroid.extensions)

            implementation(deps.androidx.activityCompose)
            implementation(deps.androidx.core.splashscreen)

            implementation(deps.google.gms.playServicesOssLicenses)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
