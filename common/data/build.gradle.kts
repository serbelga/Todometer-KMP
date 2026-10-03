plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.common.data"

        withHostTest {}
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.domain)
            implementation(projects.common.database)
            implementation(libs.sergiobelda.fonament.preferences)

            implementation(deps.jetbrains.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(deps.jetbrains.kotlinx.coroutines.test)
            implementation(deps.mockk.common)
            implementation(kotlin("test"))
        }
        getByName("androidHostTest").dependencies {
            implementation(deps.junit)
            implementation(deps.mockk.mockk)
        }
        jvmTest.dependencies {
            implementation(deps.mockk.mockk)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
