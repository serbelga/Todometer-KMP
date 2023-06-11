plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.todometer.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
    implementation(libs.dependencyGraphGenerator)
}

gradlePlugin {
    plugins {
        register("dependencyGraphGenerator") {
            id = "todometer.dependency.graph.generator"
            implementationClass = "DependencyGraphGeneratorConventionPlugin"
        }
        register("spotless") {
            id = "todometer.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
        register("commonLibraryAndroid") {
            id = "todometer.common.library.android"
            implementationClass = "CommonLibraryAndroidConventionPlugin"
        }
    }
}
