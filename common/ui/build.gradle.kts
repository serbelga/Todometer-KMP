plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.domain)

            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(libs.jetbrains.navigation.compose)
            api(libs.sergiobelda.navigationComposeExtended)
            api(libs.sergiobelda.navigationComposeExtendedAnnotation)

            api(project.dependencies.platform(libs.koin.bom))
            api(libs.koin.compose.viewmodel)

            implementation(libs.kotlin.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.coroutinesTest)
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(compose.uiTooling)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.todometer.common.ui"
}
