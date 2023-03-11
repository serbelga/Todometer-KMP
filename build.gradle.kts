@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.dependencyGraphGenerator) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.sqlDelight) apply false
    alias(libs.plugins.spotless)
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.google.firebase.crashlyticsGradle)
        classpath(libs.google.ossLicensesPlugin)
        classpath(libs.google.services)
        classpath(libs.moko.resources.generator)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

subprojects {
    if (!project.name.contains("ios")) {
        apply("${rootDir}/ktlint.gradle.kts")
    }
}
