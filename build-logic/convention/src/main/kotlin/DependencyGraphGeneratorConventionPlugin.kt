/*
 * Copyright 2023 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
