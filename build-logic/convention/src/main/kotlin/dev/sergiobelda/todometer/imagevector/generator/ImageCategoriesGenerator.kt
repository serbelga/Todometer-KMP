package dev.sergiobelda.todometer.imagevector.generator

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec

class ImageCategoriesGenerator(private val categories: List<String>) {

    fun createFileSpec(): FileSpec {
        val builder = createImagesFileSpecBuilder()
        builder.addType(createImagesTypeSpecBuilder(categories).build())
        return builder.build()
    }

    private fun createImagesFileSpecBuilder(): FileSpec.Builder {
        val imagesPackage = PackageNames.TodometerCommonDesignSystemResourcesImagesPackage.packageName
        return FileSpec.builder(
            packageName = imagesPackage,
            fileName = Images
        )
    }

    private fun createImagesTypeSpecBuilder(
        categories: List<String>
    ): TypeSpec.Builder =
        TypeSpec.objectBuilder(Images).addTypes(
            categories.map {
                TypeSpec.objectBuilder(it).build()
            }
        )

    private companion object {
        // TODO: Make Images string dynamic.
        const val Images = "Images"
    }
}
