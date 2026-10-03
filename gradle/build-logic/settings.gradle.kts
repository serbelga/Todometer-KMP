rootProject.name = "build-logic"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
    versionCatalogs {
        create("deps") {
            from("dev.sergiobelda.projectconfig.catalog:deps:2026.10.00")
        }
    }
}

include(":convention")
