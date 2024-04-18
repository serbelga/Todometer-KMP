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

package dev.sergiobelda.todometer.common.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Deprecated("Replaced by common androidx.navigation.NavHostController.")
class NavigationController {

    var navigationGraph: NavigationGraph? = null
        set(value) {
            if (navigationGraph != value) {
                currentDestinationId = value?.startDestinationId
            }
            field = value
        }

    var currentDestinationId: String? by mutableStateOf(navigationGraph?.startDestinationId)
        private set

    private val arguments: MutableMap<String, Any> = mutableMapOf()

    fun navigateTo(destinationId: String, vararg navigationArguments: Pair<String, Any>) {
        arguments.clear()
        navigationGraph?.composableNodes?.get(destinationId)?.let {
            arguments.putAll(navigationArguments)
            currentDestinationId = destinationId
        }
    }

    fun getStringArgOrNull(key: String): String? = arguments[key] as? String
}
