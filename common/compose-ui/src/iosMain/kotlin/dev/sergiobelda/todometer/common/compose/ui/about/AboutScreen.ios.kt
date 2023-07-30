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

package dev.sergiobelda.todometer.common.compose.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerTitle

@Composable
fun AboutScreen(
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
                onCardClick = { privacyPolicyDialogState = true },
                AboutItem.PrivacyPolicy
            )
        }
        AboutAppVersion()
    }
}
