package dev.sergiobelda.todometer.buildlogic.imagevectorgenerator.utils

import com.squareup.kotlinpoet.FileSpec

/**
 * Sets the indent for this [FileSpec] to match that of our code style.
 */
internal fun FileSpec.Builder.setIndent() = indent(Indent)

private val Indent = " ".repeat(4)
