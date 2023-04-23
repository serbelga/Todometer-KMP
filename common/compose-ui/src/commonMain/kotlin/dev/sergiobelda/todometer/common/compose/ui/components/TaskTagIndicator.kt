package dev.sergiobelda.todometer.common.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.ToDometerTheme
import dev.sergiobelda.todometer.common.compose.ui.mapper.composeColorOf
import dev.sergiobelda.todometer.common.domain.model.Tag

@Composable
fun RowScope.TaskTagIndicator(
    tag: Tag,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = ToDometerTheme.toDometerColors.composeColorOf(tag))
            .height(20.dp)
            .width(4.dp)
    )
    Spacer(
        modifier.size(12.dp)
    )
}
