@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.dependencyGraphGenerator) apply false
    alias(libs.plugins.firebaseCrashlytics) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.mokoResources) apply false
    alias(libs.plugins.sqlDelight) apply false
    alias(libs.plugins.spotless)
}

subprojects {
    if (!project.name.contains("ios")) {
        apply("${rootDir}/ktlint.gradle.kts")
    }
}
