plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.todometer.buildlogic"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
    implementation(libs.dependencyGraphGenerator)

    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation(libs.xmlpull)
    implementation(libs.xpp3)
}

gradlePlugin {
    plugins {
        register("commonLibraryAndroid") {
            id = "todometer.common.library.android"
            implementationClass = "CommonLibraryAndroidConventionPlugin"
        }
        register("dependencyGraphGenerator") {
            id = "todometer.dependency-graph-generator"
            implementationClass = "DependencyGraphGeneratorConventionPlugin"
        }
        register("spotless") {
            id = "todometer.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
        register("generateImages") {
            id = "todometer.common.generateImages"
            implementationClass = "imagevector.generator.plugin.ImageVectorGenerationPlugin"
        }
    }
}
