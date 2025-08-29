enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "Todometer-KMP"

include(":app")
include(":app-common:designsystem")
include(":app-common:ui")
include(":app-common:ui-tooling")
include(":app-feature:about")
include(":app-feature:addtask")
include(":app-feature:addtasklist")
include(":app-feature:edittask")
include(":app-feature:edittasklist")
include(":app-feature:home")
include(":app-feature:settings")
include(":app-feature:taskdetails")

include(":common:android")
include(":common:core")
include(":common:data")
include(":common:database")
include(":common:designsystem-resources")
include(":common:domain")
include(":common:preferences")
include(":common:resources")
include(":common:ui")
include(":common:ui-tooling")

include(":macrobenchmark")

include(":wearapp-wearos")
