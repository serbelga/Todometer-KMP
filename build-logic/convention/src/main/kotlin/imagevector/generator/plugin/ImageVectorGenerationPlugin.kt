package imagevector.generator.plugin

import imagevector.generator.task.ImageVectorGenerationTask
import org.gradle.api.Plugin
import org.gradle.api.Project

open class ImageVectorGenerationPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        ImageVectorGenerationTask.register(project)
    }
}
