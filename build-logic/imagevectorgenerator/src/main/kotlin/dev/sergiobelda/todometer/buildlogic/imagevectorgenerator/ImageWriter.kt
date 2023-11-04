package dev.sergiobelda.todometer.buildlogic.imagevectorgenerator

import java.io.File

/**
 * Generates programmatic representation of all [images] using [ImageVectorGenerator].
 *
 * @property images the list of [Image]s to generate Kotlin files for
 */
class ImageWriter(private val images: List<Image>) {
    /**
     * Generates images and writes them to [outputSrcDirectory].
     *
     * @param outputSrcDirectory the directory to generate source files in
     */
    fun generateTo(
        outputSrcDirectory: File
    ) {
        images.forEach { image ->
            val vector = ImageParser(image).parse()

            val fileSpec = ImageVectorGenerator(
                image.kotlinName,
                image.categoryName,
                vector
            ).createFileSpec()

            fileSpec.writeTo(outputSrcDirectory)
        }
    }
}
