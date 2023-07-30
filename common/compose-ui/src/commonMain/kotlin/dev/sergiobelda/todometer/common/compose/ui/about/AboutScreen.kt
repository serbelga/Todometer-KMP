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

package dev.sergiobelda.todometer.common.compose.ui.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutTopBar(navigateBack: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    ToDometerIcons.NavigateBefore,
                    contentDescription = stringResource(MR.strings.back)
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
private fun AboutItem.icon(): Painter =
    when (this) {
        AboutItem.GitHub -> ToDometerIcons.GitHub
        AboutItem.PrivacyPolicy -> ToDometerIcons.Description
        AboutItem.OpenSourceLicenses -> ToDometerIcons.Code
    }

@Composable
private fun AboutItem.text(): String =
    when (this) {
        AboutItem.GitHub -> stringResource(MR.strings.github)
        AboutItem.PrivacyPolicy -> stringResource(MR.strings.privacy_policy)
        AboutItem.OpenSourceLicenses -> stringResource(MR.strings.open_source_licenses)
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
                contentDescription = aboutItem.text()
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

const val GitHubUrl: String = "https://github.com/serbelga/ToDometer_Multiplatform"
const val PrivacyPolicyUrl: String =
    "https://github.com/serbelga/ToDometerKotlinMultiplatform/blob/main/PRIVACY_POLICY.md"

private val AboutItemCardHeight: Dp = 72.dp
private val AboutItemCardSpacing: Dp = 24.dp
private val AboutItemCardTonalElevation: Dp = 2.dp
private val AboutItemCardCornerRadius: Dp = 16.dp
