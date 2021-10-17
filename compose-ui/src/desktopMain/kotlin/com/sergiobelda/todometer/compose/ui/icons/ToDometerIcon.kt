package com.sergiobelda.todometer.compose.ui.icons

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun iconToDometer() =
    if (MaterialTheme.colors.isLight)
        painterResource("images/isotype_light.svg")
    else
        painterResource("images/isotype_dark.svg")
