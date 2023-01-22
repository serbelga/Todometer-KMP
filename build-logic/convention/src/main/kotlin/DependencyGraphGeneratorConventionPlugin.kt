import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorExtension
import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorPlugin
import guru.nidi.graphviz.attribute.Color
import guru.nidi.graphviz.attribute.Style
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class DependencyGraphGeneratorConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(DependencyGraphGeneratorPlugin::class.java)

            extensions.configure<DependencyGraphGeneratorExtension> {
                projectGenerators.create("multiplatformProjects") {
                    projectNode = { node, project ->
                        node.add(Style.FILLED)
                        if (project.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
                            node.add(Color.rgb("#7F52FF").fill())
                        }
                        node
                    }
                }
            }
        }
    }
}
