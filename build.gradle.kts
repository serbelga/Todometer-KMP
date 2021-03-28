buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.kotlinPlugin)
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.SqlDelight.gradlePlugin)
    }
}

group = "com.sergiobelda.todometer"
version = "1.0"

allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}