plugins {
    alias(deps.plugins.android.kotlinMultiplatformLibrary)
    alias(deps.plugins.cashapp.sqldelight)
    alias(deps.plugins.jetbrains.kotlin.multiplatform)
    alias(deps.plugins.sergiobelda.convention.lint)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
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
            implementation(deps.cashapp.sqldelight.coroutines)
            implementation(deps.cashapp.sqldelight.primitiveAdapters)

            implementation(projects.common.domain)
        }
        commonTest.dependencies {
            implementation(deps.jetbrains.kotlinx.coroutines.test)
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            implementation(deps.cashapp.sqldelight.androidDriver)
        }
        getByName("androidHostTest").dependencies {
            implementation(deps.cashapp.sqldelight.jvmDriver)
        }
        jvmMain.dependencies {
            implementation(deps.cashapp.sqldelight.jvmDriver)
        }
        iosMain.dependencies {
            implementation(deps.cashapp.sqldelight.nativeDriver)
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
