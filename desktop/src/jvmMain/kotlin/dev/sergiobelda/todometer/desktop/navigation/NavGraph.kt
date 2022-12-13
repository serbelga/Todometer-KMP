/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.desktop.navigation

import androidx.compose.runtime.Composable

class NavGraph(
    val startDestinationId: String? = null,
    val composableNodes: Map<String, @Composable () -> Unit> = mapOf()
) {

    private constructor(builder: Builder) : this(builder.startDestinationId, builder.composableNodes)

    class Builder {

        var startDestinationId: String? = null

        private val _composableNodes: MutableMap<String, @Composable () -> Unit> = mutableMapOf()
        val composableNodes: Map<String, @Composable () -> Unit> get() = _composableNodes

        fun addDestination(destinationId: String, content: @Composable () -> Unit) {
            _composableNodes[destinationId] = content
        }

        fun build(): NavGraph = NavGraph(this)
    }
}

fun NavGraph.Builder.composableNode(
    destinationId: String,
    content: @Composable () -> Unit
) {
    addDestination(destinationId, content)
}
