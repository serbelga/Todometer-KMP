package dev.sergiobelda.todometer.common.compose.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
            GitHubAboutItemCard(onCardClick = navigateToGitHub)
            PrivacyPolicyAboutItemCard(onCardClick = { privacyPolicyDialogState = true })
            OpenSourceLicensesAboutItemCard(onCardClick = navigateToOpenSourceLicenses)
        }
        AboutAppVersion()
    }
}
