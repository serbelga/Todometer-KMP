package dev.sergiobelda.todometer.imagevector.generator

import java.io.File

class ImageCategoriesWriter(private val categories: List<String>) {

    fun generateTo(outputSrcDirectory: File) {
        val fileSpec = ImageCategoriesGenerator(categories).createFileSpec()
        fileSpec.writeTo(outputSrcDirectory)
    }
}
