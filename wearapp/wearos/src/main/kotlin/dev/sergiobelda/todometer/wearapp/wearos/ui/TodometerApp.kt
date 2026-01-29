/*
 * Copyright 2021 Sergio Belda
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

package dev.sergiobelda.todometer.wearapp.wearos.ui

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import dev.sergiobelda.navigation.compose.extended.rememberNavAction
import dev.sergiobelda.todometer.common.resources.ProvideTodometerStrings
import dev.sergiobelda.todometer.wearapp.wearos.ui.theme.TodometerTheme

@Composable
fun TodometerApp() {
    val navController = rememberSwipeDismissableNavController()
    val navAction = rememberNavAction(navController)

    ProvideTodometerStrings {
        TodometerTheme {
            TodometerNavHost(navController = navController, navAction = navAction)
        }
    }
}
