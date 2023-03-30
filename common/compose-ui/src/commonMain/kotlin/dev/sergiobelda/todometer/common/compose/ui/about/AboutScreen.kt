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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.painterResource
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
internal fun AboutAppVersion() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Text(
            text = versionName() ?: "",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}

@Composable
internal fun GitHubAboutItemCard(onCardClick: () -> Unit) {
    AboutItemCard(
        onCardClick = onCardClick,
        icon = {
            Icon(
                painterResource(ToDometerIcons.GitHub),
                contentDescription = stringResource(MR.strings.github)
            )
        },
        text = { Text(stringResource(MR.strings.github)) }
    )
}

@Composable
internal fun PrivacyPolicyAboutItemCard(onCardClick: () -> Unit) {
    AboutItemCard(
        onCardClick = onCardClick,
        icon = {
            Icon(
                painterResource(ToDometerIcons.Description),
                contentDescription = stringResource(MR.strings.privacy_policy)
            )
        },
        text = { Text(stringResource(MR.strings.privacy_policy)) }
    )
}

@Composable
internal fun OpenSourceLicensesAboutItemCard(onCardClick: () -> Unit) {
    AboutItemCard(
        onCardClick = onCardClick,
        icon = {
            Icon(
                painterResource(ToDometerIcons.Code),
                contentDescription = stringResource(MR.strings.open_source_licenses)
            )
        },
        text = { Text(stringResource(MR.strings.open_source_licenses)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutTopBar(navigateBack: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    painterResource(ToDometerIcons.ArrowBack),
                    contentDescription = stringResource(MR.strings.back)
                )
            }
        },
        title = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutItemCard(
    onCardClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier.height(81.dp).fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.width(24.dp))
            icon()
            Spacer(modifier = Modifier.width(24.dp))
            text()
        }
    }
}

const val GitHubUrl: String = "https://github.com/serbelga/ToDometer_Multiplatform"
