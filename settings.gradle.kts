enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "ToDometer-KMP"

include(":android")
include(":common:android")
include(":common:core")
include(":common:compose-ui")
include(":common:compose-ui-designsystem")
include(":common:data")
include(":common:database")
include(":common:domain")
include(":common:navigation")
include(":common:preferences")
include(":common:resources")
include(":common:ui")
include(":desktop")
include(":ios-compose-ui")
include(":wearos")
