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

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sergiobelda.todometer.app.feature.about.ui.AboutDestination
import dev.sergiobelda.todometer.app.feature.about.ui.AboutScreen
import dev.sergiobelda.todometer.app.feature.about.ui.GitHubUrl
import dev.sergiobelda.todometer.app.feature.about.ui.PrivacyPolicyUrl
import dev.sergiobelda.todometer.common.android.extensions.launchActivity
import dev.sergiobelda.todometer.common.android.extensions.openWebPage

internal actual fun NavGraphBuilder.aboutNode(
    navigateBack: () -> Unit
) {
    composable(AboutDestination.route) {
        val context = LocalContext.current
        AboutScreen(
            navigateToGitHub = { context.openWebPage(GitHubUrl) },
            navigateToPrivacyPolicy = { context.openWebPage(PrivacyPolicyUrl) },
            navigateToOpenSourceLicenses = { context.launchActivity<OssLicensesMenuActivity>() },
            navigateBack = navigateBack
        )
    }
}
