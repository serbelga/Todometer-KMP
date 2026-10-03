plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(deps.plugins.sergiobelda.convention.lint)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    android {
        namespace = "dev.sergiobelda.todometer.common.database"

        withHostTest {}
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.sqldelight.coroutines)
            implementation(libs.sqldelight.primitiveAdapters)

            implementation(projects.common.domain)
        }
        commonTest.dependencies {
            implementation(deps.jetbrains.kotlinx.coroutines.test)
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(libs.sqldelight.androidDriver)
        }
        getByName("androidHostTest").dependencies {
            implementation(libs.sqldelight.jvmDriver)
        }
        jvmMain.dependencies {
            implementation(libs.sqldelight.jvmDriver)
        }
        iosMain.dependencies {
            implementation(libs.sqldelight.nativeDriver)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

sqldelight {
    databases {
        create("TodometerDatabase") {
            packageName.set("dev.sergiobelda.todometer.common.database")
        }
    }
}
