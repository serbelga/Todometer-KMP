pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
    
}
rootProject.name = "ToDometer"


include(":android")
include(":desktop")
include(":iosApp")
include(":compose-ui")
include(":common")
