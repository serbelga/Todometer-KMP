buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.kotlin.serialization)
        classpath(libs.android.gradlePlugin)
        classpath(libs.google.firebase.crashlyticsGradle)
        classpath(libs.google.ossLicensesPlugin)
        classpath(libs.google.services)
        classpath(libs.sqldelight.gradlePlugin)
    }
}

group = "dev.sergiobelda.todometer"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

subprojects {
    if (!project.name.contains("ios")) {
        apply {
            from("${rootDir}/ktlint.gradle.kts")
        }
    }
}
