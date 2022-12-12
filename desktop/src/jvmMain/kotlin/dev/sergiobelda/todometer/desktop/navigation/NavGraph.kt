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

interface Destination {
    val route: String
}

class NavGraph {

    var startDestination: Destination? = null

    private var _destinations: MutableList<Destination> = mutableListOf()
    val destinations: List<Destination> get() = _destinations

    fun addDestination(destination: Destination) {
        _destinations.add(destination)
    }

    fun clear() {
        _destinations.clear()
    }
}
