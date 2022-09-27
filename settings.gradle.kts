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
include(":common:android-resources")
include(":common:core")
include(":common:compose-ui")
include(":common:data")
include(":common:database")
include(":common:domain")
include(":common:network")
include(":common:preferences")
include(":common:ui")
include(":desktop")
include(":ios")
include(":wearos")
