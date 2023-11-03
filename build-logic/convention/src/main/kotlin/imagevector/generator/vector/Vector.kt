package imagevector.generator.vector

/**
 * Simplified representation of a vector, with root [nodes].
 *
 * @param nodes may either be a singleton list of the root group, or a list of root paths / groups
 * if there are multiple top level declaration
 */
class Vector(
    val width: String,
    val height: String,
    val viewportWidth: Float,
    val viewportHeight: Float,
    val nodes: List<VectorNode>
)

sealed class VectorNode {
    class Group(val paths: MutableList<Path> = mutableListOf()) : VectorNode()
    class Path(
        val strokeAlpha: Float,
        val fillAlpha: Float,
        val fillColor: String,
        val fillType: FillType,
        val nodes: List<PathNode>
    ) : VectorNode()
}
