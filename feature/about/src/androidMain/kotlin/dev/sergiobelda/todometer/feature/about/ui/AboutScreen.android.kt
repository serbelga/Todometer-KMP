/*
 * Copyright 2023 Sergio Belda
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

package dev.sergiobelda.todometer.feature.about.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.ui.components.TodometerTitle

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
