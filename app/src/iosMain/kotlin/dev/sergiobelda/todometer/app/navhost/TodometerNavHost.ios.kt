/*
 * Copyright 2024 Sergio Belda
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

package dev.sergiobelda.todometer.app.navhost

import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavGraphBuilder
import dev.sergiobelda.fonament.di.koin.koinFonamentViewModel
import dev.sergiobelda.fonament.presentation.navigation.NavigationNode
import dev.sergiobelda.navigation.compose.extended.composable
import dev.sergiobelda.todometer.app.feature.about.navigation.aboutNavigationEventHandler
import dev.sergiobelda.todometer.app.feature.about.ui.AboutNavDestination
import dev.sergiobelda.todometer.app.feature.about.ui.AboutScreen
import dev.sergiobelda.todometer.app.feature.about.ui.GITHUB_URL
import dev.sergiobelda.todometer.app.feature.about.ui.PRIVACY_POLICY_URL

internal actual fun NavGraphBuilder.aboutNode(navigateBack: () -> Unit) {
    composable(
        navDestination = AboutNavDestination,
    ) {
        val uriHandler = LocalUriHandler.current
        val aboutNavigationEventHandler =
            aboutNavigationEventHandler(
                navigateBack = navigateBack,
                navigateToGitHub = { uriHandler.openUri(GITHUB_URL) },
                navigateToPrivacyPolicy = { uriHandler.openUri(PRIVACY_POLICY_URL) },
            )
        AboutScreen.NavigationNode(
            viewModel = koinFonamentViewModel(),
            navigationEventHandler = aboutNavigationEventHandler,
        )
    }
}
