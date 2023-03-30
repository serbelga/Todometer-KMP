package dev.sergiobelda.todometer.ui.about

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.compose.ui.about.AboutScreen

@Composable
internal fun AboutRoute(
    navigateToGitHub: () -> Unit,
    navigateToOpenSourceLicenses: () -> Unit,
    navigateBack: () -> Unit
) {
    AboutScreen(
        navigateToGitHub = navigateToGitHub,
        navigateToOpenSourceLicenses = navigateToOpenSourceLicenses,
        navigateBack = navigateBack
    )
}
