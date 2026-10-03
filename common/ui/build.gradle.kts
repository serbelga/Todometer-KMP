plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.compose)
    alias(deps.plugins.jetbrains.kotlin.composeCompiler)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.common.ui"
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(projects.common.di)
            api(projects.common.domain)
            api(projects.common.resources)
            api(projects.common.uiTooling)

            api(libs.jetbrains.androidx.navigation.compose)
            api(deps.jetbrains.compose.foundation)
            api(deps.jetbrains.compose.material3)
            api(deps.jetbrains.compose.runtime)
            api(deps.jetbrains.compose.ui)
            api(deps.jetbrains.kotlinx.collections.immutable)
            api(deps.jetbrains.kotlinx.datetime)

            api(libs.sergiobelda.navigationComposeExtended)
            api(libs.sergiobelda.navigationComposeExtendedAnnotation)
        }
        commonTest.dependencies {
            implementation(deps.jetbrains.kotlinx.coroutines.test)
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            api(deps.jetbrains.compose.animationGraphics)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
