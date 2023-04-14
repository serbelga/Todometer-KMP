plugins {
    application
    kotlin("jvm") version libs.versions.kotlin.get()
    alias(libs.plugins.kotlinSerialization)
}

group = "dev.sergiobelda.todometer.backend"
version = "1.0-SNAPSHOT"

dependencies {

    implementation(libs.ktor.serialization.gson)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.contentNegotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.locations)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)

    implementation(libs.logback)

    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)

    implementation(libs.h2Database)
    implementation(libs.hikariCP)

    // Koin for Ktor
    implementation(libs.koin.ktor)
    // SLF4J Logger
    implementation(libs.koin.logger)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "18"
    }
}

application {
    mainClass.set("dev.sergiobelda.todometer.backend.ApplicationKt")
}
