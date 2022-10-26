enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "ToDometerMultiplatform"

include(":android")
include(":backend")
include(":common:android")
include(":common:core")
include(":common:compose-ui")
include(":common:compose-ui-designsystem")
include(":common:data")
include(":common:database")
include(":common:domain")
include(":common:network")
include(":common:preferences")
include(":common:resources")
include(":common:ui")
include(":desktop")
include(":ios")
include(":ios-compose-ui-experimental")
include(":wearos")
