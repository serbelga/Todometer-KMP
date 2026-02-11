import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("dev.sergiobelda.gradle.spotless")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(projects.app.shared)

    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "dev.sergiobelda.todometer.app.MainKt"
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
