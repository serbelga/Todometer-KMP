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

package dev.sergiobelda.todometer.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class Action(private val navController: NavHostController) {
    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigate(navigationParams: NavigationParams) {
        if (navigationParams.destination is TopLevelDestination) {
            navController.navigate(navigationParams.navigationRoute) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        } else {
            navController.navigate(navigationParams.navigationRoute)
        }
    }
}

fun openWebPage(context: Context, url: String) {
    val webpage: Uri = Uri.parse(url)
    ContextCompat.startActivity(
        context,
        Intent(Intent.ACTION_VIEW, webpage),
        null
    )
}
