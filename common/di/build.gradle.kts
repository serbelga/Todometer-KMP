plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(deps.plugins.sergiobelda.convention.lint)
    alias(libs.plugins.sergiobelda.todometer.common.library.android)
    alias(libs.plugins.sergiobelda.todometer.dependencyGraphGenerator)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.common.di"
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(project.dependencies.platform(deps.koin.bom))
            api(deps.koin.compose)
            api(deps.koin.composeViewmodel)
            api(deps.koin.core)
            api(deps.koin.test)
        }
        androidMain.dependencies {
            api(deps.koin.android)
            api(deps.koin.androidXCompose)
        }
    }
}
