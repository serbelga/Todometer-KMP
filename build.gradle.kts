plugins {
    alias(deps.plugins.android.application) apply false
    alias(deps.plugins.android.kotlinMultiplatformLibrary) apply false
    alias(deps.plugins.android.library) apply false
    alias(deps.plugins.android.test) apply false
    alias(deps.plugins.androidx.baselineprofile) apply false
    alias(deps.plugins.cashapp.sqldelight) apply false
    alias(deps.plugins.detekt) apply false
    alias(deps.plugins.google.firebase.crashlytics) apply false
    alias(deps.plugins.google.firebase.perf) apply false
    alias(deps.plugins.google.gms.services) apply false
    alias(deps.plugins.google.ksp) apply false
    alias(deps.plugins.jetbrains.kotlin.composeCompiler) apply false
    alias(deps.plugins.jetbrains.kotlin.jvm) apply false
    alias(deps.plugins.jetbrains.kotlin.multiplatform) apply false
    alias(deps.plugins.spotless) apply false
    alias(libs.plugins.dependencyGraphGenerator) apply false
    alias(libs.plugins.sergiobelda.composeVectorize) apply false
}

buildscript {
    dependencies {
        classpath(deps.google.gms.ossLicensesPlugin)
    }
}

apply(from = "./gradle/scripts/git/git-hooks.gradle.kts")
