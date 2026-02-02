package dev.sergiobelda.todometer.app.feature.about.preview

import androidx.compose.runtime.Composable
import dev.sergiobelda.fonament.presentation.ui.NoContentState
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLandscape
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLightDark
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.PreviewLocales
import dev.sergiobelda.todometer.app.common.ui.tooling.preview.TodometerAppPreview
import dev.sergiobelda.todometer.app.feature.about.ui.AboutContent
import dev.sergiobelda.todometer.app.feature.about.ui.AboutUIState

@PreviewLocales
@PreviewLightDark
@PreviewLandscape
@Composable
private fun AboutContentPreview() {
    TodometerAppPreview {
        AboutContent(
            uiState = AboutUIState,
            contentState = NoContentState,
        )
    }
}
