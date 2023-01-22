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
        classpath(libs.moko.resources.generator)
        classpath(libs.sqldelight.gradlePlugin)
        classpath(libs.spotless.gradlePlugin)
        classpath(libs.dependencyGraphGenerator)
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
