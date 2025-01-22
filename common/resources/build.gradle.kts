import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.composeCompiler)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.sergiobelda.gradle.common.library.android)
    alias(libs.plugins.sergiobelda.gradle.dependencyGraphGenerator)
    alias(libs.plugins.sergiobelda.gradle.lint)
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            api(libs.lyricist.lyricist)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    sourceSets["main"].resources.srcDir("src/commonMain/resources")

    namespace = "dev.sergiobelda.todometer.common.resources"

    lint {
        abortOnError = false
    }
}

// region Lyricist Multiplatform setup
dependencies {
    add("kspCommonMainMetadata", libs.lyricist.processor)
}

// Workaround for KSP only in Common Main.
// https://github.com/google/ksp/issues/567
tasks.withType<KotlinCompilationTask<*>>().all {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}
// endregion
