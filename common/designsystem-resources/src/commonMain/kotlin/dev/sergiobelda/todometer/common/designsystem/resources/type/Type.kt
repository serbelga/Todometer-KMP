package dev.sergiobelda.todometer.common.designsystem.resources.type

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.ResourceItem

val quickSandFontBold: Font
    @Composable get() = resolveFont(
        "quickSandFontBold",
        "font/quicksand_bold.ttf",
        FontWeight.Bold
    )

val quickSandFontMedium: Font
    @Composable get() = resolveFont(
        "quickSandFontMedium",
        "font/quicksand_medium.ttf",
        FontWeight.Medium
    )

val quickSandFontSemiBold: Font
    @Composable get() = resolveFont(
        "quickSandFontSemiBold",
        "font/quicksand_semi_bold.ttf",
        FontWeight.SemiBold
    )

val quickSandFontFamily: FontFamily
    @Composable get() = FontFamily(
        quickSandFontBold,
        quickSandFontMedium,
        quickSandFontSemiBold,
    )

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun resolveFont(
    id: String,
    path: String,
    weight: FontWeight = FontWeight.Normal,
    style: FontStyle = FontStyle.Normal
): Font =
    Font(
        FontResource(
            id,
            setOf(ResourceItem(
                    setOf(),
                    path
                ),
            )
        ),
        weight,
        style
    )
