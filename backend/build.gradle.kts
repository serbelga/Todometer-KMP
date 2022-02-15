/*
 * Copyright 2021 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    application
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {

    implementation(libs.ktor.auth)
    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.core.jvm)
    implementation(libs.ktor.gson)
    implementation(libs.ktor.locations)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.server.core)
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
        kotlinOptions.jvmTarget = "1.8"
    }
}

application {
    mainClass.set("dev.sergiobelda.todometer.backend.ApplicationKt")
}
