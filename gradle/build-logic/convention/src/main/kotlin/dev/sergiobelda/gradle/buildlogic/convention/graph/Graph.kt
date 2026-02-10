package dev.sergiobelda.gradle.buildlogic.convention.graph

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.CacheableTask

private class Graph(
    private val root: Project,
)

internal enum class PluginType {
    AndroidApplication,
    AndroidLibrary,
    Ios,
    Js,
    Jvm,
    KotlinMultiplatformLibrary,
}

internal fun Project.configureGraphTasks() {

}

@CacheableTask
private abstract class GraphDumpTask : DefaultTask() {


}

@CacheableTask
private abstract class GraphUpdateTask : DefaultTask() {

}
