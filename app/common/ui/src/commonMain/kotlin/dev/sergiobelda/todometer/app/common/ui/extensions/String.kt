package dev.sergiobelda.todometer.app.common.ui.extensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import dev.sergiobelda.todometer.app.common.designsystem.theme.Alpha.applyMediumEmphasisAlpha
import dev.sergiobelda.todometer.common.resources.TodometerResources

@Composable
fun String.addStyledOptionalSuffix(): AnnotatedString = buildAnnotatedString {
    append(this@addStyledOptionalSuffix)
    withStyle(
        style = SpanStyle(color = MaterialTheme.colorScheme.onBackground.applyMediumEmphasisAlpha())
    ) {
        append(" · ")
        append(TodometerResources.strings.optional)
    }
}
