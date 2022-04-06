enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
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
include(":common")
include(":common-android-resources")
include(":common-compose-ui")
include(":desktop")
include(":ios")
include(":wearos")
include(":common-data")
include(":common-domain")
include(":common-preferences")
