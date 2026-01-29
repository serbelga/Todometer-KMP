plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidLibrary {
        namespace = "dev.sergiobelda.todometer.common.database"

        withHostTest {}
    }
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.sqldelight.coroutines)
            implementation(libs.sqldelight.primitiveAdapters)

            implementation(projects.common.domain)
        }
        commonTest.dependencies {
            implementation(libs.jetbrains.kotlin.coroutines.test)
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
