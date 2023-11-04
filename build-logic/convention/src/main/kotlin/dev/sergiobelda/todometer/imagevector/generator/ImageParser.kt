package dev.sergiobelda.todometer.imagevector.generator

import dev.sergiobelda.todometer.imagevector.generator.vector.FillType
import dev.sergiobelda.todometer.imagevector.generator.vector.PathParser
import dev.sergiobelda.todometer.imagevector.generator.vector.Vector
import dev.sergiobelda.todometer.imagevector.generator.vector.VectorNode
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParser.END_DOCUMENT
import org.xmlpull.v1.XmlPullParser.END_TAG
import org.xmlpull.v1.XmlPullParser.START_TAG
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

/**
 * Parser that converts [image]s into [Vector]s
 */
class ImageParser(private val image: Image) {

    /**
     * @return a [Vector] representing the provided [image].
     */
    fun parse(): Vector {
        val parser = XmlPullParserFactory.newInstance().newPullParser().apply {
            setInput(image.fileContent.byteInputStream(), null)
            seekToStartTag()
        }

        check(parser.name == VECTOR) { "The start tag must be <vector>!" }

        var width = ""
        var height = ""
        var viewportWidth = 0f
        var viewportHeight = 0f
        val nodes = mutableListOf<VectorNode>()

        var currentGroup: VectorNode.Group? = null

        while (!parser.isAtEnd()) {
            when (parser.eventType) {
                START_TAG -> {
                    when (parser.name) {
                        VECTOR -> {
                            width = parser.getValueAsString(WIDTH).processDpDimension()
                            height = parser.getValueAsString(HEIGHT).processDpDimension()
                            viewportWidth = parser.getValueAsFloat(VIEWPORT_WIDTH) ?: 0f
                            viewportHeight = parser.getValueAsFloat(VIEWPORT_HEIGHT) ?: 0f
                        }

                        PATH -> {
                            val pathData = parser.getAttributeValue(
                                null,
                                PATH_DATA
                            )
                            val fillAlpha = parser.getValueAsFloat(FILL_ALPHA)
                            val strokeAlpha = parser.getValueAsFloat(STROKE_ALPHA)
                            val fillColor = parser.getValueAsString(FILL_COLOR).processFillColor()

                            val fillType = when (parser.getAttributeValue(null, FILL_TYPE)) {
                                // evenOdd and nonZero are the only supported values here, where
                                // nonZero is the default if no values are defined.
                                EVEN_ODD -> FillType.EvenOdd
                                else -> FillType.NonZero
                            }
                            val path = VectorNode.Path(
                                strokeAlpha = strokeAlpha ?: 1f,
                                fillAlpha = fillAlpha ?: 1f,
                                fillColor = fillColor.uppercase(),
                                fillType = fillType,
                                nodes = PathParser.parsePathString(pathData)
                            )
                            if (currentGroup != null) {
                                currentGroup.paths.add(path)
                            } else {
                                nodes.add(path)
                            }
                        }

                        GROUP -> {
                            val group = VectorNode.Group()
                            currentGroup = group
                            nodes.add(group)
                        }

                        CLIP_PATH -> {

                        }
                    }
                }
            }
            parser.next()
        }

        return Vector(
            width = width,
            height = height,
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            nodes = nodes
        )
    }
}

/**
 * @return the float value for the attribute [name], or null if it couldn't be found
 */
private fun XmlPullParser.getValueAsFloat(name: String): Float? =
    getAttributeValue(null, name)?.toFloatOrNull()

/**
 * @return the string value for the attribute [name], or null if it couldn't be found
 */
private fun XmlPullParser.getValueAsString(name: String): String =
    getAttributeValue(null, name)

private fun XmlPullParser.seekToStartTag(): XmlPullParser {
    var type = next()
    while (type != START_TAG && type != END_DOCUMENT) {
        // Empty loop
        type = next()
    }
    if (type != START_TAG) {
        throw XmlPullParserException("No start tag found")
    }
    return this
}

private fun XmlPullParser.isAtEnd() =
    eventType == END_DOCUMENT || (depth < 1 && eventType == END_TAG)

private fun String.processDpDimension(): String =
    this.replace("dp", "")

private fun String.processFillColor(): String {
    val diff = ARGB_HEXADECIMAL_COLOR_LENGTH - this.length
    return if (diff > 0) {
        this.replace("#", "#${"F".repeat(diff)}")
    } else this
}

private const val ARGB_HEXADECIMAL_COLOR_LENGTH = 9

// XML tag names
private const val VECTOR = "vector"
private const val CLIP_PATH = "clip-path"
private const val GROUP = "group"
private const val PATH = "path"

// XML attribute names
private const val FILL_ALPHA = "android:fillAlpha"
private const val FILL_COLOR = "android:fillColor"
private const val FILL_TYPE = "android:fillType"
private const val HEIGHT = "android:height"
private const val PATH_DATA = "android:pathData"
private const val STROKE_ALPHA = "android:strokeAlpha"
private const val VIEWPORT_HEIGHT = "android:viewportHeight"
private const val VIEWPORT_WIDTH = "android:viewportWidth"
private const val WIDTH = "android:width"

// XML attribute values
private const val EVEN_ODD = "evenOdd"
