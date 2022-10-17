@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
}

group = "dev.sergiobelda.todometer.ios"

kotlin {
    iosX64("uikitX64") {
        binaries {
            executable {
                entryPoint = "dev.sergiobelda.todometer.ios.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }
    iosArm64("uikitArm64") {
        binaries {
            executable {
                entryPoint = "dev.sergiobelda.todometer.ios.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }

    sourceSets {
        val uikitMain by creating {
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            dependencies {
                // implementation(projects.common.composeUi)
                // implementation(projects.common.composeUiDesignsystem)
                implementation(projects.common.core)
                implementation(projects.common.domain)
                implementation(projects.common.ui)

                implementation(compose.material)
                implementation(compose.material3)
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
        bundleIdPrefix = "dev.sergiobelda.todometer.ios"
        projectName = "ToDometer"
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
