package dev.sergiobelda.todometer.common.compose.ui.task

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.components.ToDometerChip
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerColors
import dev.sergiobelda.todometer.common.compose.ui.theme.TodometerTypography
import dev.sergiobelda.todometer.common.compose.ui.theme.onSurfaceMediumEmphasis

@Composable
fun TaskChecklistItemChip(checklistItemsDone: Long, totalChecklistItems: Long) {
    ToDometerChip(modifier = Modifier.padding(bottom = 8.dp)) {
        Icon(
            Icons.Outlined.CheckBox,
            contentDescription = null,
            modifier = Modifier.size(16.dp).padding(end = 4.dp),
            tint = TodometerColors.onSurfaceMediumEmphasis
        )
        Text(
            "$checklistItemsDone/$totalChecklistItems",
            style = TodometerTypography.caption,
            color = TodometerColors.onSurfaceMediumEmphasis
        )
    }
}
