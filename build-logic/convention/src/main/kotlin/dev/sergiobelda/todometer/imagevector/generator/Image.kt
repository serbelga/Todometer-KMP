package dev.sergiobelda.todometer.imagevector.generator

/**
 * Represents a image's Kotlin name, image category name, processed XML file name, and XML file content.
 *
 * The [kotlinName] is typically the PascalCase equivalent of the original image name, with the
 * caveat that images starting with a number are prefixed with an underscore.
 *
 * @property kotlinName the name of the generated Kotlin property, for example `ArrowBack`.
 * @property categoryName the image category name, for example `Icons`.
 * @property xmlFileName the name of the processed XML file.
 * @property fileContent the content of the source XML file that will be parsed.
 */
data class Image(
    val kotlinName: String,
    val categoryName: String,
    val xmlFileName: String,
    val fileContent: String
)
