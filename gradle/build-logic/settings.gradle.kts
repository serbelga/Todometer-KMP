rootProject.name = "build-logic"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
    versionCatalogs {
        create("deps") {
            from("dev.sergiobelda.projectconfig.catalog:deps:2026.10.03")
        }
    }
}

include(":convention")
