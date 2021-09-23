package com.sergiobelda.todometer.ui.about

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sergiobelda.todometer.android.R

@Composable
fun PrivacyPolicyDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = {
            Text(stringResource(R.string.privacy_policy))
        },
        onDismissRequest = onDismissRequest,
        text = {
            Text(stringResource(R.string.privacy_policy_body))
        },
        confirmButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(stringResource(android.R.string.ok))
            }
        }
    )
}
