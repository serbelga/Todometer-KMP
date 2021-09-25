package com.sergiobelda.todometer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.compose.mapper.composeColorOf
import com.sergiobelda.todometer.compose.ui.theme.TodometerColors
import com.sergiobelda.todometer.compose.ui.theme.TodometerTypography
import kotlinx.coroutines.launch

@Composable
fun TodometerTagSelector(selectedTag: Tag, onSelected: (Tag) -> Unit) {
    val tags = enumValues<Tag>()
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    Text(
        text = stringResource(R.string.choose_tag),
        color = TodometerColors.primary,
        style = TodometerTypography.caption,
        modifier = Modifier.padding(start = 32.dp)
    )
    LazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        state = state
    ) {
        items(tags) { tag ->
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .clickable {
                        onSelected(tag)
                    }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(TodometerColors.composeColorOf(tag))
                ) {
                    if (tag == selectedTag) {
                        Icon(
                            Icons.Rounded.Check,
                            "Selected",
                            tint = TodometerColors.onPrimary
                        )
                    }
                }
            }
        }
    }
    scope.launch {
        state.animateScrollToItem(tags.indexOf(selectedTag))
    }
}
