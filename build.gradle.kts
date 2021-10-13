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
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.4")
        classpath(Libs.SqlDelight.gradlePlugin)
    }
}

group = "com.sergiobelda.todometer"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
        google()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

subprojects {
    if (!project.name.contains("ios")) {
        apply {
            from("${project.rootDir}/gradle/ktlint.gradle.kts")
        }
    }
}
