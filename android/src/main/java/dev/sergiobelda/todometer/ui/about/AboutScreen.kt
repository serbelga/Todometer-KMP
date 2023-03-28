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

package dev.sergiobelda.todometer.ui.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.android.extensions.getVersionName
import dev.sergiobelda.todometer.common.compose.ui.about.AboutItemCard
import dev.sergiobelda.todometer.common.compose.ui.about.AboutTopBar
import dev.sergiobelda.todometer.common.compose.ui.components.title.ToDometerTitle
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.painterResource
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navigateToGitHub: () -> Unit,
    navigateToOpenSourceLicenses: () -> Unit,
    navigateBack: () -> Unit
) {
    var privacyPolicyDialogState by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { AboutTopBar(navigateBack = navigateBack) }
    ) { paddingValues ->
        if (privacyPolicyDialogState) {
            PrivacyPolicyDialog { privacyPolicyDialogState = false }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ) {
            ToDometerTitle()
            Spacer(modifier = Modifier.height(72.dp))
            AboutItemCard(
                onCardClick = navigateToGitHub,
                icon = {
                    Icon(
                        painterResource(ToDometerIcons.GitHub),
                        contentDescription = stringResource(MR.strings.github)
                    )
                },
                text = { Text(stringResource(MR.strings.github)) }
            )
            AboutItemCard(
                onCardClick = { privacyPolicyDialogState = true },
                icon = {
                    Icon(
                        painterResource(ToDometerIcons.Description),
                        contentDescription = stringResource(MR.strings.privacy_policy)
                    )
                },
                text = { Text(stringResource(MR.strings.privacy_policy)) }
            )
            AboutItemCard(
                onCardClick = navigateToOpenSourceLicenses,
                icon = {
                    Icon(
                        painterResource(ToDometerIcons.Code),
                        contentDescription = stringResource(MR.strings.open_source_licenses)
                    )
                },
                text = { Text(stringResource(MR.strings.open_source_licenses)) }
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Text(
                text = LocalContext.current.getVersionName() ?: "",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
