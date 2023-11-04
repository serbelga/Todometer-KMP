package dev.sergiobelda.todometer.imagevector.generator

import dev.sergiobelda.todometer.imagevector.generator.utils.toKotlinPropertyName
import java.io.File

class ImageCategoriesProcessor(private val imagesDirectories: List<File>) {

    fun process(): List<String> = loadImageCategories()

    private fun loadImageCategories(): List<String> =
        imagesDirectories.map {
            it.name.toKotlinPropertyName()
        }
}
