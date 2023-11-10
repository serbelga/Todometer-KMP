import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
    id("todometer.dependency-graph-generator")
}

group = "dev.sergiobelda.todometer.app.ios"

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
                implementation(projects.appCommon.designsystem)
                implementation(projects.appCommon.ui)
                implementation(projects.appFeature.about)
                implementation(projects.appFeature.addtask)
                implementation(projects.appFeature.addtasklist)
                implementation(projects.appFeature.edittask)
                implementation(projects.appFeature.edittasklist)
                implementation(projects.appFeature.home)
                implementation(projects.appFeature.settings)
                implementation(projects.appFeature.taskdetails)
                implementation(projects.common.core)
                implementation(projects.common.domain)
                implementation(projects.common.ui)
            }
        }
        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
