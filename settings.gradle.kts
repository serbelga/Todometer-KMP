pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "ToDometer Multiplatform"

include(":android")
include(":desktop")
include(":ios")
include(":common-compose-ui")
include(":common")
include(":backend")
include(":wearos")
