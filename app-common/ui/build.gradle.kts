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
        namespace = "dev.sergiobelda.todometer.app.common.ui"
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.common.ui)
            api(projects.appCommon.designsystem)

            implementation(libs.sergiobelda.pigment)
        }
        commonTest.dependencies {
            implementation(deps.jetbrains.kotlinx.coroutines.test)
            implementation(deps.mockk.common)
            implementation(kotlin("test"))
        }
        jvmMain.dependencies {
            api(deps.jetbrains.kotlinx.coroutines.swing)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
