package dev.sergiobelda.todometer.buildlogic.imagevectorgenerator

import dev.sergiobelda.todometer.buildlogic.imagevectorgenerator.utils.toKotlinPropertyName
import java.io.File
import java.util.Locale

class ImageProcessor(private val imagesDirectories: List<File>) {

    fun process(): List<Image> = loadImages()

    private fun loadImages(): List<Image> =
        imagesDirectories.flatMap { dir ->
            val images = dir.walk().filter { !it.isDirectory }.toList()

            val transformedImages = images.map { file ->
                val filename = file.nameWithoutExtension
                val kotlinName = filename.toKotlinPropertyName()

                val fileContent = file.readText()
                Image(
                    kotlinName = kotlinName,
                    categoryName = dir.name.toKotlinPropertyName(),
                    xmlFileName = filename,
                    fileContent = processXmlFile(fileContent),
                )
            }

            // Ensure image names are unique when accounting for case insensitive filesystems -
            // workaround for b/216295020
            transformedImages
                .groupBy { it.kotlinName.lowercase(Locale.ROOT) }
                .filter { it.value.size > 1 }
                .forEach { entry ->
                    throw IllegalStateException(
                        """Found multiple images with the same case-insensitive filename:
                        | ${entry.value.joinToString()}. Generating images with the same
                        | case-insensitive filename will cause issues on devices without
                        | a case sensitive filesystem (OSX / Windows).""".trimMargin()
                    )
                }

            transformedImages
        }
}

/**
 * Processes the given [fileContent] by removing android theme attributes and values.
 */
private fun processXmlFile(fileContent: String): String {
    // Remove any defined tint for paths that use theme attributes
    val tintAttribute = Regex.escape("""android:tint="?attr/colorControlNormal"""")
    val tintRegex = """\n.*?$tintAttribute""".toRegex(RegexOption.MULTILINE)

    return fileContent
        .replace(tintRegex, "")
        // The imported images have white as the default path color, so let's change it to be
        // black as is typical on Android.
        .replace("@android:color/white", "#FF000000")
}
