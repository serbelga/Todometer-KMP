package dev.sergiobelda.todometer.buildlogic.imagevectorgenerator.vector

/**
 * Determines the winding rule that decides how the interior of a [VectorNode.Path] is calculated.
 *
 * This maps to [android.graphics.Path.FillType] used in the framework, and can be defined in XML
 * via `android:fillType`.
 */
enum class FillType {
    NonZero,
    EvenOdd
}
