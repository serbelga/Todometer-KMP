import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
    id("todometer.dependency-graph-generator")
}

group = "dev.sergiobelda.todometer.desktop"
version = "1.0.0-beta02"

kotlin {
    jvm {
        compilations.all {
            kotlin {
                jvmToolchain(17)
            }
            kotlinOptions {
                freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            }
        }
    }
    sourceSets {
        val jvmMain by getting {
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
                implementation(projects.common.designsystemResources)
                implementation(projects.common.domain)
                implementation(projects.common.navigation)
                implementation(projects.common.resources)
                implementation(projects.common.ui)
                implementation(projects.common.viewmodel)

                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "dev.sergiobelda.todometer.desktop.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            modules(
                "java.instrument",
                "java.management",
                "java.sql",
                "jdk.unsupported",
                "java.naming"
            )
            packageName = "ToDometer"
            packageVersion = "1.0.0"
            copyright = "© 2023 Sergio Belda. Licensed under the Apache License."
            licenseFile.set(project.file("../LICENSE"))

            macOS {
                iconFile.set(project.file("./launcher_icons/macos_icon.icns"))
            }
            windows {
                iconFile.set(project.file("./launcher_icons/windows_icon.ico"))
            }
            linux {
                iconFile.set(project.file("./launcher_icons/linux_icon.png"))
            }
        }
    }
}
