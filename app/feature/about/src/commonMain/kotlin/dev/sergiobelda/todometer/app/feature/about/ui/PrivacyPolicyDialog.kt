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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
internal fun PrivacyPolicyDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = {
            Text(TodometerResources.strings.privacy_policy)
        },
        onDismissRequest = onDismissRequest,
        text = {
            Column {
                PrivacyPolicyDialogSectionTitle(TodometerResources.strings.privacy_policy_user_data)
                Text(TodometerResources.strings.privacy_policy_user_data_body)
                PrivacyPolicyDialogSeparator()
                PrivacyPolicyDialogSectionTitle(TodometerResources.strings.privacy_policy_permissions)
                Text(TodometerResources.strings.privacy_policy_permissions_body)
                PrivacyPolicyDialogSeparator()
                PrivacyPolicyDialogSectionTitle(TodometerResources.strings.privacy_policy_device_and_network_abuse)
                Text(TodometerResources.strings.privacy_policy_device_and_network_abuse_body)
                PrivacyPolicyDialogSeparator()
                PrivacyPolicyDialogSectionTitle(TodometerResources.strings.privacy_policy_public)
                Text(TodometerResources.strings.privacy_policy_public_body)
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(TodometerResources.strings.ok)
            }
        }
    )
}

@Composable
private fun PrivacyPolicyDialogSectionTitle(text: String) {
    Text(text = text, fontWeight = FontWeight.Bold)
}

@Composable
private fun PrivacyPolicyDialogSeparator() {
    Spacer(modifier = Modifier.height(8.dp))
}
