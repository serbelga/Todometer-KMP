plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.todometer.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
    implementation("com.vanniktech:gradle-dependency-graph-generator-plugin:0.8.0")
}

gradlePlugin {
    plugins {
        register("dependency-graph-generator") {
            id = "todometer.dependency-graph-generator"
            implementationClass = "DependencyGraphGeneratorConventionPlugin"
        }
        register("spotless") {
            id = "todometer.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
    }
}
