package dev.sergiobelda.todometer.imagevector.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

/**
 * Package names used for image generation.
 */
enum class PackageNames(val packageName: String) {
    TodometerCommonDesignSystemResourcesPackage("dev.sergiobelda.todometer.common.designsystem.resources"),
    TodometerCommonDesignSystemResourcesImagesPackage(TodometerCommonDesignSystemResourcesPackage.packageName + ".images"),
    GraphicsPackage("androidx.compose.ui.graphics"),
    VectorPackage(GraphicsPackage.packageName + ".vector")
}

/**
 * [ClassName]s used for image generation.
 */
object ClassNames {
    val ImageVector = PackageNames.VectorPackage.className("ImageVector")
    val PathFillType = PackageNames.GraphicsPackage.className("PathFillType", "Companion")
}

/**
 * [MemberName]s used for image generation.
 */
object MemberNames {
    val Color = MemberName(PackageNames.GraphicsPackage.packageName, "Color")
    val EvenOdd = MemberName(ClassNames.PathFillType, "EvenOdd")
    val Group = MemberName(PackageNames.VectorPackage.packageName, "group")
    val ImageVector = MemberName(PackageNames.TodometerCommonDesignSystemResourcesPackage.packageName, "imageVector")
    val Path = MemberName(PackageNames.VectorPackage.packageName, "path")
    val SolidColor = MemberName(PackageNames.GraphicsPackage.packageName, "SolidColor")
}

/**
 * @return the [ClassName] of the given [classNames] inside this package.
 */
fun PackageNames.className(vararg classNames: String) = ClassName(this.packageName, *classNames)
