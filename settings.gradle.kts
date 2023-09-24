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

rootProject.name = "Todometer-KMP"

include(":android")
include(":common:android")
include(":common:core")
include(":common:data")
include(":common:database")
include(":common:designsystem")
include(":common:designsystem-resources")
include(":common:domain")
include(":common:navigation")
include(":common:preferences")
include(":common:resources")
include(":common:ui")
include(":common:viewmodel")
include(":desktop")
include(":feature:about")
include(":feature:addtask")
include(":feature:addtasklist")
include(":feature:edittask")
include(":feature:edittasklist")
include(":feature:home")
include(":feature:settings")
include(":feature:taskdetails")
include(":ios-compose-ui")
include(":wearos")