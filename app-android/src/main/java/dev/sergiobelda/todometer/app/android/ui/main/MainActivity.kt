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

package dev.sergiobelda.todometer.app.android.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.todometer.app.android.ui.navhost.TodometerNavHost
import dev.sergiobelda.todometer.app.common.ui.theme.TodometerAppTheme
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.navigation.Action
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MainContent()
        }
    }

    @Composable
    private fun MainContent(mainViewModel: MainViewModel = getViewModel()) {
        val navController = rememberNavController()
        val action = remember(navController) { Action(navController) }

        val appThemeState = mainViewModel.appTheme.collectAsStateWithLifecycle()
        val darkTheme: Boolean = when (appThemeState.value) {
            AppTheme.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            AppTheme.DARK_THEME -> true
            AppTheme.LIGHT_THEME -> false
        }

        DisposableEffect(darkTheme) {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) {
                    darkTheme
                }
            )
            onDispose {}
        }

        TodometerAppTheme(darkTheme) {
            TodometerNavHost(navController, action)
        }
    }
}
