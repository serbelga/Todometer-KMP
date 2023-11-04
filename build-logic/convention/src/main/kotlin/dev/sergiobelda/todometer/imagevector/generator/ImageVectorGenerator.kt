package dev.sergiobelda.todometer.imagevector.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.buildCodeBlock
import dev.sergiobelda.todometer.imagevector.generator.utils.setIndent
import dev.sergiobelda.todometer.imagevector.generator.vector.FillType
import dev.sergiobelda.todometer.imagevector.generator.vector.Vector
import dev.sergiobelda.todometer.imagevector.generator.vector.VectorNode
import java.util.Locale

/**
 * Generator for creating a Kotlin source file with an ImageVector property for the given [vector],
 * with name [imageName].
 *
 * @param imageName the name for the generated property, which is also used for the generated file.
 * I.e if the name is `Menu`, the property will be `Menu` (inside a theme receiver object) and
 * the file will be `Menu.kt` (under the theme package name).
 * @param vector the parsed vector to generate ImageVector.Builder commands for
 */
class ImageVectorGenerator(
    private val imageName: String,
    private val imageCategoryName: String,
    private val vector: Vector
) {
    /**
     * @return a [FileSpec] representing a Kotlin source file containing the property for this
     * programmatic [vector] representation.
     *
     * The package name and hence file location of the generated file is:
     * [PackageNames.TodometerCommonDesignSystemResourcesImagesPackage].
     */
    fun createFileSpec(): FileSpec {
        val builder = createFileSpecBuilder()
        val backingProperty = getBackingProperty()
        val propertySpecBuilder =
            PropertySpec.builder(name = imageName, type = ClassNames.ImageVector)
                .receiver(
                    ClassName(
                        PackageNames.TodometerCommonDesignSystemResourcesImagesPackage.packageName,
                        // TODO: Make Images string dynamic.
                        "Images",
                        imageCategoryName
                    )
                )
                .getter(
                    imageGetter(
                        backingProperty = backingProperty,
                        imageName = imageName
                    )
                )
        builder.addProperty(propertySpecBuilder.build())
        builder.addProperty(backingProperty)
        return builder.setIndent().build()
    }

    private fun createFileSpecBuilder(): FileSpec.Builder {
        val imagesPackage =
            PackageNames.TodometerCommonDesignSystemResourcesImagesPackage.packageName
                .plus(".")
                .plus(imageCategoryName.lowercase(Locale.ROOT))
        return FileSpec.builder(
            packageName = imagesPackage,
            fileName = imageName
        )
    }

    private fun getBackingProperty(): PropertySpec {
        val backingPropertyName = "_" + imageName.replaceFirstChar { it.lowercase(Locale.ROOT) }
        return backingProperty(name = backingPropertyName)
    }

    private fun imageGetter(
        backingProperty: PropertySpec,
        imageName: String
    ): FunSpec {
        return FunSpec.getterBuilder()
            .addCode(
                buildCodeBlock {
                    beginControlFlow("if (%N != null)", backingProperty)
                    addStatement("return %N!!", backingProperty)
                    endControlFlow()
                }
            )
            .addCode(
                buildCodeBlock {
                    val controlFlow = buildString {
                        append("%N = %M(\n")
                        append("    name = \"%N\",\n")
                        append("    width = ${vector.width}f,\n")
                        append("    height = ${vector.height}f,\n")
                        append("    viewportWidth = ${vector.viewportWidth}f,\n")
                        append("    viewportHeight = ${vector.viewportHeight}f\n")
                        append(")")
                    }
                    beginControlFlow(
                        controlFlow,
                        backingProperty,
                        MemberNames.ImageVector,
                        imageName
                    )
                    vector.nodes.forEach { node -> addRecursively(node) }
                    endControlFlow()
                }
            )
            .addStatement("return %N!!", backingProperty)
            .build()
    }

    private fun backingProperty(name: String): PropertySpec {
        val nullableImageVector = ClassNames.ImageVector.copy(nullable = true)
        return PropertySpec.builder(name = name, type = nullableImageVector)
            .mutable()
            .addModifiers(KModifier.PRIVATE)
            .initializer("null")
            .build()
    }
}

/**
 * Recursively adds function calls to construct the given [vectorNode] and its children.
 */
private fun CodeBlock.Builder.addRecursively(vectorNode: VectorNode) {
    when (vectorNode) {
        is VectorNode.Group -> {
            beginControlFlow("%M", MemberNames.Group)
            vectorNode.paths.forEach { path ->
                addRecursively(path)
            }
            endControlFlow()
        }

        is VectorNode.Path -> {
            addPath(vectorNode) {
                vectorNode.nodes.forEach { pathNode ->
                    addStatement(pathNode.asFunctionCall())
                }
            }
        }
    }
}

/**
 * Adds a function call to create the given [path], with [pathBody] containing the commands for
 * the path.
 */
private fun CodeBlock.Builder.addPath(
    path: VectorNode.Path,
    pathBody: CodeBlock.Builder.() -> Unit
) {
    // Only set the fill type if it is EvenOdd - otherwise it will just be the default.
    val setFillType = path.fillType == FillType.EvenOdd

    val parameterList = with(path) {
        listOfNotNull(
            "fill = %M(%M(${fillColor.replace("#", "0x")}))",
            "fillAlpha = ${fillAlpha}f".takeIf { fillAlpha != 1f },
            "strokeAlpha = ${strokeAlpha}f".takeIf { strokeAlpha != 1f },
            "pathFillType = %M".takeIf { setFillType },
        )
    }

    val parameters = if (parameterList.isNotEmpty()) {
        parameterList.joinToString(prefix = "(", postfix = ")")
    } else {
        ""
    }

    if (setFillType) {
        beginControlFlow(
            "%M$parameters",
            MemberNames.Path,
            MemberNames.SolidColor,
            MemberNames.Color,
            MemberNames.EvenOdd
        )
    } else {
        beginControlFlow(
            "%M$parameters",
            MemberNames.Path,
            MemberNames.SolidColor,
            MemberNames.Color
        )
    }
    pathBody()
    endControlFlow()
}
