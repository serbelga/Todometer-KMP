package dev.sergiobelda.todometer.imagevector.generator.utils

import org.gradle.kotlin.dsl.support.uppercaseFirstChar

/**
 * Converts a snake_case name to a KotlinProperty name.
 *
 * If the first character of [this] is a digit, the resulting name will be prefixed with an `_`
 */
internal fun String.toKotlinPropertyName(): String {
    val pattern = "_[a-z]".toRegex()
    return replace(pattern) { it.value.last().uppercase() }.let {
        if (it.first().isDigit()) "_$it" else it.uppercaseFirstChar()
    }
}
