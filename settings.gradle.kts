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

include(":app-android")
include(":app-common:designsystem")
include(":app-common:ui")
include(":app-desktop")
include(":app-feature:about")
include(":app-feature:addtask")
include(":app-feature:addtasklist")
include(":app-feature:edittask")
include(":app-feature:edittasklist")
include(":app-feature:home")
include(":app-feature:settings")
include(":app-feature:taskdetails")
include(":app-ios")

include(":common:android")
include(":common:core")
include(":common:data")
include(":common:database")
include(":common:designsystem-resources")
include(":common:designsystem-resources:imagevector-generator")
include(":common:domain")
include(":common:navigation")
include(":common:preferences")
include(":common:resources")
include(":common:ui")
include(":common:viewmodel")

include(":wear-app")