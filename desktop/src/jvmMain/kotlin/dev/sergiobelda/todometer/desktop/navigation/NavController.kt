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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NavController(private val navGraph: NavGraph) {
    var currentDestination: Destination? by mutableStateOf(navGraph.startDestination)
        private set

    var composablesMap: MutableMap<String, @Composable () -> Unit> = mutableMapOf()

    fun navigateTo(destination: Destination) {
        navGraph.destinations.find { it == destination }?.let {
            currentDestination = destination
        }
    }
}
