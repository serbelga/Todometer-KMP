import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
    id("todometer.dependency-graph-generator")
}

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
                implementation(projects.common.designsystemResources)
                implementation(projects.common.domain)
                implementation(projects.common.navigation)
                implementation(projects.common.resources)
                implementation(projects.common.ui)
                implementation(projects.common.viewmodel)

                implementation(compose.desktop.currentOs)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "dev.sergiobelda.todometer.app.desktop.TodometerAppKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            modules(
                "java.instrument",
                "java.management",
                "java.sql",
                "jdk.unsupported",
                "java.naming"
            )
            packageName = "Todometer"
            packageVersion = "1.1.0"
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
