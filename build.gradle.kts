buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.kotlinPlugin)
        classpath(Libs.kotlinSerialization)
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.SqlDelight.gradlePlugin)
    }
}

group = "com.sergiobelda.todometer"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

subprojects {
    if (!project.name.contains("ios")) {
        apply {
            from("${project.rootDir}/gradle/ktlint.gradle.kts")
        }
    }
}
