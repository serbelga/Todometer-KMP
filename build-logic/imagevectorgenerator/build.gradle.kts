plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.todometer.buildlogic.imagevectorgenerator"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
    implementation(libs.squareup.kotlinpoet)
    implementation(libs.xmlpull)
    implementation(libs.xpp3)
}
