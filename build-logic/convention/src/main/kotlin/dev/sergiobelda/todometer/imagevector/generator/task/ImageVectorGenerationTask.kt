package dev.sergiobelda.todometer.imagevector.generator.task

import dev.sergiobelda.todometer.imagevector.generator.Image
import dev.sergiobelda.todometer.imagevector.generator.ImageCategoriesProcessor
import dev.sergiobelda.todometer.imagevector.generator.ImageCategoriesWriter
import dev.sergiobelda.todometer.imagevector.generator.ImageProcessor
import dev.sergiobelda.todometer.imagevector.generator.ImageWriter
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File

@CacheableTask
open class ImageVectorGenerationTask : DefaultTask() {

    @PathSensitive(PathSensitivity.RELATIVE)
    @InputDirectory
    val imagesDirectory = project.projectDir.resolve("xml-images")

    @Internal
    fun getImageDirectories(): List<File> =
        imagesDirectory.listFiles()?.filter { it.isDirectory } ?: emptyList()

    @OutputDirectory
    lateinit var buildDirectory: File

    private fun loadImageCategories(): List<String> =
        ImageCategoriesProcessor(
            getImageDirectories()
        ).process()

    private fun loadImages(): List<Image> =
        ImageProcessor(
            getImageDirectories()
        ).process()

    @get:OutputDirectory
    val generatedSrcMainDirectory: File
        get() = buildDirectory.resolve(GeneratedSrcMain)

    @TaskAction
    fun run() {
        ImageCategoriesWriter(loadImageCategories()).generateTo(generatedSrcMainDirectory)
        ImageWriter(loadImages()).generateTo(generatedSrcMainDirectory)
    }

    companion object {
        fun register(project: Project) {
            val (task, buildDirectory) = project.registerGenerationTask(
                "generateImages",
                ImageVectorGenerationTask::class.java
            )
            registerImageGenerationTask(project, task, buildDirectory)
        }

        const val GeneratedSrcMain = "src/commonMain/kotlin"
    }
}

@Suppress("DEPRECATION")
fun Project.registerGenerationTask(
    taskName: String,
    taskClass: Class<ImageVectorGenerationTask>
): Pair<TaskProvider<ImageVectorGenerationTask>, File> {
    val buildDirectory = project.buildDir.resolve("images/")

    return tasks.register(taskName, taskClass) {
        this.buildDirectory = buildDirectory
    } to buildDirectory
}

fun Project.getMultiplatformSourceSet(name: String): KotlinSourceSet {
    val sourceSet = project.multiplatformExtension!!.sourceSets.find { it.name == name }
    return requireNotNull(sourceSet) {
        "No source sets found matching $name"
    }
}

private val Project.multiplatformExtension
    get() = extensions.findByType(KotlinMultiplatformExtension::class.java)

private fun registerImageGenerationTask(
    project: Project,
    task: TaskProvider<*>,
    buildDirectory: File
) {
    val sourceSet = project.getMultiplatformSourceSet(KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME)
    val generatedSrcMainDirectory =
        buildDirectory.resolve(ImageVectorGenerationTask.GeneratedSrcMain)
    sourceSet.kotlin.srcDir(project.files(generatedSrcMainDirectory).builtBy(task))
}
