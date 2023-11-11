import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
    id("todometer.dependency-graph-generator")
}

kotlin {
    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "app-ios"
            isStatic = true
            xcf.add(this)
        }
    }

    sourceSets {
        val iosMain by creating {
            dependencies {
                implementation(projects.app.common.designsystem)
                implementation(projects.app.common.ui)
                implementation(projects.app.feature.about)
                implementation(projects.app.feature.addtask)
                implementation(projects.app.feature.addtasklist)
                implementation(projects.app.feature.edittask)
                implementation(projects.app.feature.edittasklist)
                implementation(projects.app.feature.home)
                implementation(projects.app.feature.settings)
                implementation(projects.app.feature.taskdetails)
                implementation(projects.common.core)
                implementation(projects.common.domain)
                implementation(projects.common.ui)
            }
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
