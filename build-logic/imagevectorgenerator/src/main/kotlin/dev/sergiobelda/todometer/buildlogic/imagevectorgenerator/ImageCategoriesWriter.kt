package dev.sergiobelda.todometer.buildlogic.imagevectorgenerator

import java.io.File

class ImageCategoriesWriter(private val categories: List<String>) {

    fun generateTo(outputSrcDirectory: File) {
        val fileSpec = ImageCategoriesGenerator(categories).createFileSpec()
        fileSpec.writeTo(outputSrcDirectory)
    }
}
