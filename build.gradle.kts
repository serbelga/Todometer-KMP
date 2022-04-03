buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.kotlin.gradlePluginz)
        classpath(libs.kotlin.serialization)
        classpath(libs.android.gradlePluginz)
        classpath(libs.google.firebase.crashlyticsGradle)
        classpath(libs.google.ossLicensesPluginz)
        classpath(libs.google.services)
        classpath(libs.sqldelight.gradlePluginz)
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
