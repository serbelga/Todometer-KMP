import org.jetbrains.compose.desktop.application.dsl.TargetFormat

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
    id("todometer.dependency-graph-generator")
}

group = "dev.sergiobelda.todometer.desktop"
version = "1.0.0-alpha01"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
                freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            }
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.common.core)
                implementation(projects.common.composeUi)
                implementation(projects.common.composeUiDesignsystem)
                implementation(projects.common.domain)
                implementation(projects.common.navigation)
                implementation(projects.common.resources)
                implementation(projects.common.ui)
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
            packageVersion = "1.0.0"
            licenseFile.set(project.file("../LICENSE"))
        }
    }
}
