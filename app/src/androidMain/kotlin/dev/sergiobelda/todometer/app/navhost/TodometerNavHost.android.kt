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

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavGraphBuilder
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sergiobelda.navigation.compose.extended.composable
import dev.sergiobelda.todometer.app.feature.about.navigation.AboutNavigationEventHandler
import dev.sergiobelda.todometer.app.feature.about.ui.AboutNavDestination
import dev.sergiobelda.todometer.app.feature.about.ui.AboutScreen
import dev.sergiobelda.todometer.app.feature.about.ui.AboutViewModel
import dev.sergiobelda.todometer.app.feature.about.ui.GitHubUrl
import dev.sergiobelda.todometer.app.feature.about.ui.PrivacyPolicyUrl
import dev.sergiobelda.todometer.common.android.extensions.launchActivity
import dev.sergiobelda.todometer.common.ui.base.navigation.NavigationNodeContent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named

internal actual fun NavGraphBuilder.aboutNode(
    navigateBack: () -> Unit,
) {
    composable(
        navDestination = AboutNavDestination,
    ) {
        val context = LocalContext.current
        val uriHandler = LocalUriHandler.current
        val aboutNavigationEventHandler = remember {
            AboutNavigationEventHandler(
                navigateBack = navigateBack,
                navigateToGitHub = { uriHandler.openUri(GitHubUrl) },
                navigateToOpenSourceLicenses = { context.launchActivity<OssLicensesMenuActivity>() },
                navigateToPrivacyPolicy = { uriHandler.openUri(PrivacyPolicyUrl) },
            )
        }
        AboutScreen.NavigationNodeContent(
            viewModel = koinViewModel(named<AboutViewModel>()),
            navigationEventHandler = aboutNavigationEventHandler,
        )
    }
}
