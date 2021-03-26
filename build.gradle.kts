buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
        classpath("com.android.tools.build:gradle:4.0.2")
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