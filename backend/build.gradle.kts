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

    implementation(Libs.Ktor.clientApache)
    implementation(Libs.Ktor.auth)
    implementation(Libs.Ktor.locations)
    implementation(Libs.Ktor.clientCore)
    implementation(Libs.Ktor.clientCoreJvm)
    implementation(Libs.Ktor.serverCore)
    implementation(Libs.Ktor.gson)
    implementation(Libs.Ktor.serialization)
    implementation(Libs.Ktor.serverNetty)
    testImplementation(Libs.Ktor.serverTests)

    implementation(Libs.logback)

    implementation(Libs.Exposed.core)
    implementation(Libs.Exposed.jdbc)

    implementation(Libs.h2Database)
    implementation(Libs.hikariCP)

    // Koin for Ktor
    implementation(Libs.Koin.ktor)
    // SLF4J Logger
    implementation(Libs.Koin.logger)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

application {
    mainClass.set("com.sergiobelda.backend.ApplicationKt")
}
