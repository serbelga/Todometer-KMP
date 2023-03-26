/*
 * Copyright 2023 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.todometer.common.compose.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.designsystem.theme.Alpha
import dev.sergiobelda.todometer.common.compose.ui.preferences.themeIcon
import dev.sergiobelda.todometer.common.compose.ui.preferences.themeName
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.painterResource
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigateBack: () -> Unit,
) {
    var appTheme by remember { mutableStateOf(AppTheme.FOLLOW_SYSTEM) }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painterResource(ToDometerIcons.ArrowBack),
                            contentDescription = stringResource(MR.strings.back)
                        )
                    }
                },
                title = {
                    Text(stringResource(MR.strings.settings))
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SettingsChooseAppTheme(appTheme, onItemClick = { appTheme = it })
        }
    }
}

@Composable
private fun SettingsChooseAppTheme(appTheme: AppTheme, onItemClick: (AppTheme) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(SettingsItemPaddingHorizontal)) {
        Text(text = stringResource(MR.strings.theme))
        Spacer(modifier = Modifier.height(SettingsItemInternalSpacing))
        Row(
            horizontalArrangement = Arrangement.spacedBy(AppThemeItemsSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            enumValues<AppTheme>().forEach {
                AppThemeItem(
                    it,
                    onClick = { onItemClick(it) },
                    it == appTheme
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppThemeItem(
    appTheme: AppTheme,
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val selectedAlpha = if (selected) Alpha.High else Alpha.Disabled
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(AppThemeItemCornerRadius),
        border = BorderStroke(
            width = AppThemeItemBorder,
            color = MaterialTheme.colorScheme.outline.copy(alpha = selectedAlpha)
        ),
        modifier = modifier.height(AppThemeItemHeight).width(AppThemeItemWidth),
        tonalElevation = if (selected) AppThemeItemSelectedElevation else AppThemeItemUnselectedElevation,
        contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = selectedAlpha)
    ) {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Icon(
                    appTheme.themeIcon(),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(AppThemeItemSelectedSpacing))
                Text(
                    appTheme.themeName(),
                    textAlign = TextAlign.Center,
                    maxLines = AppThemeItemMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private val SettingsItemPaddingHorizontal: Dp = 16.dp
private val SettingsItemInternalSpacing: Dp = 16.dp

private val AppThemeItemsSpacing: Dp = 12.dp

private val AppThemeItemSelectedElevation: Dp = 6.dp
private val AppThemeItemUnselectedElevation: Dp = 0.dp
private val AppThemeItemCornerRadius: Dp = 8.dp
private val AppThemeItemBorder: Dp = 1.dp
private val AppThemeItemHeight: Dp = 96.dp
private val AppThemeItemWidth: Dp = 120.dp
private val AppThemeItemSelectedSpacing: Dp = 6.dp
private const val AppThemeItemMaxLines: Int = 2
