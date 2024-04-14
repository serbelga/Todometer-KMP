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

package dev.sergiobelda.todometer.app.feature.about.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.app.common.ui.components.TodometerTitle
import dev.sergiobelda.todometer.common.designsystem.resources.images.Images
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Code
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Description
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.Github
import dev.sergiobelda.todometer.common.designsystem.resources.images.icons.NavigateBefore
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
fun AboutScreen(
    navigateToGitHub: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    navigateToOpenSourceLicenses: () -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = { AboutTopBar(navigateBack = navigateBack) }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ) {
            TodometerTitle()
            Spacer(modifier = Modifier.height(72.dp))
            AboutItemCard(onCardClick = navigateToGitHub, AboutItem.GitHub)
            AboutItemCard(onCardClick = navigateToPrivacyPolicy, AboutItem.PrivacyPolicy)
            AboutItemCard(onCardClick = navigateToOpenSourceLicenses, AboutItem.OpenSourceLicenses)
        }
        AboutAppVersion()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutTopBar(navigateBack: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    Images.Icons.NavigateBefore,
                    contentDescription = TodometerResources.strings.back
                )
            }
        },
        title = {}
    )
}

internal enum class AboutItem {
    GitHub,
    PrivacyPolicy,
    OpenSourceLicenses
}

@Composable
private fun AboutItem.icon(): ImageVector =
    when (this) {
        AboutItem.GitHub -> Images.Icons.Github
        AboutItem.PrivacyPolicy -> Images.Icons.Description
        AboutItem.OpenSourceLicenses -> Images.Icons.Code
    }

@Composable
private fun AboutItem.text(): String =
    when (this) {
        AboutItem.GitHub -> TodometerResources.strings.github
        AboutItem.PrivacyPolicy -> TodometerResources.strings.privacy_policy
        AboutItem.OpenSourceLicenses -> TodometerResources.strings.open_source_licenses
    }

@Composable
internal fun AboutItemCard(
    onCardClick: () -> Unit,
    aboutItem: AboutItem,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onCardClick,
        shape = RoundedCornerShape(AboutItemCardCornerRadius),
        tonalElevation = AboutItemCardTonalElevation,
        modifier = modifier.height(AboutItemCardHeight).fillMaxWidth().padding(8.dp),
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.width(AboutItemCardSpacing))
            Icon(
                aboutItem.icon(),
                contentDescription = aboutItem.text(),
                modifier = Modifier.size(AboutItemCardIconSize)
            )
            Spacer(modifier = Modifier.width(AboutItemCardSpacing))
            Text(aboutItem.text())
        }
    }
}

@Composable
internal fun AboutAppVersion() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Text(
            text = appVersionName() ?: "",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

const val GitHubUrl: String = "https://github.com/serbelga/Todometer-KMP"
const val PrivacyPolicyUrl: String =
    "https://github.com/serbelga/Todometer-KMP/blob/main/PRIVACY_POLICY.md"

private val AboutItemCardHeight: Dp = 72.dp
private val AboutItemCardSpacing: Dp = 24.dp
private val AboutItemCardIconSize: Dp = 24.dp
private val AboutItemCardTonalElevation: Dp = 2.dp
private val AboutItemCardCornerRadius: Dp = 16.dp
