import org.jetbrains.compose.experimental.uikit.tasks.ExperimentalPackComposeApplicationForXCodeTask
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import kotlin.reflect.full.declaredMemberProperties

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.composeMultiplatform)
    id("todometer.spotless")
    id("todometer.dependency-graph-generator")
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
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics",
                    "-linker-option", "-lsqlite3"
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
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics",
                    "-linker-option", "-lsqlite3"
                )
            }
        }
    }

    sourceSets {
        val uikitMain by creating {
            dependencies {
                implementation(projects.common.core)
                implementation(projects.common.designsystem)
                implementation(projects.common.domain)
                implementation(projects.common.ui)
                implementation(projects.feature.about)
                implementation(projects.feature.addtask)
                implementation(projects.feature.addtasklist)
                implementation(projects.feature.edittask)
                implementation(projects.feature.edittasklist)
                implementation(projects.feature.home)
                implementation(projects.feature.settings)
                implementation(projects.feature.taskdetails)
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

// Workaround to use Moko resources in ios.
// copy .bundle from all .klib to .kexe
tasks.withType<KotlinNativeLink>()
    .configureEach {
        val linkTask: KotlinNativeLink = this
        val outputDir: File = this.outputFile.get().parentFile

        @Suppress("ObjectLiteralToLambda") // lambda broke up-to-date
        val action = object : Action<Task> {
            override fun execute(t: Task) {
                (linkTask.libraries + linkTask.sources)
                    .filter { library -> library.extension == "klib" }
                    .filter(File::exists)
                    .forEach { inputFile ->
                        val klibKonan = org.jetbrains.kotlin.konan.file.File(inputFile.path)
                        val klib = org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl(
                            klib = klibKonan,
                            component = "default"
                        )
                        val layout = klib.extractingToTemp

                        // extracting bundles
                        layout
                            .resourcesDir
                            .absolutePath
                            .let(::File)
                            .listFiles { file: File -> file.extension == "bundle" }
                            // copying bundles to app
                            ?.forEach {
                                logger.info("${it.absolutePath} copying to $outputDir")
                                it.copyRecursively(
                                    target = File(outputDir, it.name),
                                    overwrite = true
                                )
                            }
                    }
            }
        }
        doLast(action)
    }

// copy .bundle from .kexe to .app
tasks.withType<ExperimentalPackComposeApplicationForXCodeTask>()
    .configureEach {
        val packTask: ExperimentalPackComposeApplicationForXCodeTask = this

        val kclass = ExperimentalPackComposeApplicationForXCodeTask::class
        val kotlinBinaryField =
            kclass.declaredMemberProperties.single { it.name == "kotlinBinary" }
        val destinationDirField =
            kclass.declaredMemberProperties.single { it.name == "destinationDir" }
        val executablePathField =
            kclass.declaredMemberProperties.single { it.name == "executablePath" }

        @Suppress("ObjectLiteralToLambda") // lambda broke up-to-date
        val action = object : Action<Task> {
            override fun execute(t: Task) {
                val kotlinBinary: RegularFile =
                    (kotlinBinaryField.get(packTask) as RegularFileProperty).get()
                val destinationDir: Directory =
                    (destinationDirField.get(packTask) as DirectoryProperty).get()
                val executablePath: String =
                    (executablePathField.get(packTask) as Provider<String>).get()

                val outputDir: File = File(destinationDir.asFile, executablePath).parentFile

                val bundleSearchDir: File = kotlinBinary.asFile.parentFile
                bundleSearchDir
                    .listFiles { file: File -> file.extension == "bundle" }
                    ?.forEach { file ->
                        file.copyRecursively(File(outputDir, file.name), true)
                    }
            }
        }
        doLast(action)
    }
