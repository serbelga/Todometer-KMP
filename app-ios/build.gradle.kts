plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
    id("todometer.dependency-graph-generator")
}

group = "dev.sergiobelda.todometer.app.ios"

kotlin {
    iosX64("uikitX64") {
        binaries {
            executable {
                entryPoint = "dev.sergiobelda.todometer.app.ios.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics",
                    "-linker-option", "-lsqlite3"
                )
            }
        }
    }
    iosArm64("uikitArm64") {
        binaries {
            executable {
                entryPoint = "dev.sergiobelda.todometer.app.ios.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics",
                    "-linker-option", "-lsqlite3"
                )
            }
        }
    }

    sourceSets {
        val uikitMain by creating {
            dependencies {
                implementation(projects.appCommon.designsystem)
                implementation(projects.appCommon.ui)
                implementation(projects.appFeature.about)
                implementation(projects.appFeature.addtask)
                implementation(projects.appFeature.addtasklist)
                implementation(projects.appFeature.edittask)
                implementation(projects.appFeature.edittasklist)
                implementation(projects.appFeature.home)
                implementation(projects.appFeature.settings)
                implementation(projects.appFeature.taskdetails)
                implementation(projects.common.core)
                implementation(projects.common.domain)
                implementation(projects.common.ui)
            }
        }
        val uikitX64Main by getting {
            dependsOn(uikitMain)
        }
        val uikitArm64Main by getting {
            dependsOn(uikitMain)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

compose.experimental {
    uikit.application {
        bundleIdPrefix = "dev.sergiobelda.todometer.app.ios"
        projectName = "Todometer"
        deployConfigurations {
            simulator("IPhone13ProMax") {
                //Usage: ./gradlew iosDeployIPhone13ProMaxDebug
                device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_PRO_MAX
            }
            simulator("IPad") {
                //Usage: ./gradlew iosDeployIPadDebug
                device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPAD_MINI_6th_Gen
            }
            connectedDevice("Device") {
                //First need specify your teamId here, or in local.properties (compose.ios.teamId=***)
                //teamId="***"
                //Usage: ./gradlew iosDeployDeviceRelease
            }
        }
    }
}
