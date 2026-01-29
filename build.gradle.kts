plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlinMultiplatformLibrary) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
    alias(libs.plugins.dependencyGraphGenerator) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.google.firebaseCrashlytics) apply false
    alias(libs.plugins.google.firebasePerf) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.jetbrains.composeCompiler) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.jetbrains.kotlinMultiplatform) apply false
    alias(libs.plugins.sergiobelda.composeVectorize) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.sqlDelight) apply false
}

buildscript {
    dependencies {
        classpath(libs.google.ossLicensesPlugin)
    }
}

apply(from = "./gradle/scripts/git/git-hooks.gradle.kts")
