/*
 * Copyright 2021 Sergio Belda
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

package dev.sergiobelda.todometer.ui.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.compose.ui.theme.ToDometerTheme
import dev.sergiobelda.todometer.extensions.getVersionName
import dev.sergiobelda.todometer.extensions.launchActivity
import dev.sergiobelda.todometer.extensions.openWebPage
import dev.sergiobelda.todometer.ui.components.ToDometerTitle

private const val GITHUB_URL = "https://github.com/serbelga/ToDometer_Multiplatform"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutScreen(
    navigateBack: () -> Unit
) {
    var privacyPolicyDialogState by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = ToDometerTheme.toDometerColors.onSurfaceMediumEmphasis
                        )
                    }
                },
                title = {}
            )
        }
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
            AboutItemCard(
                onCardClick = { context.openWebPage(GITHUB_URL) },
                icon = {
                    Icon(
                        painterResource(R.drawable.ic_github_24),
                        contentDescription = stringResource(R.string.github)
                    )
                },
                text = { Text(stringResource(R.string.github)) }
            )
            AboutItemCard(
                onCardClick = { privacyPolicyDialogState = true },
                icon = {
                    Icon(
                        Icons.Rounded.Description,
                        contentDescription = stringResource(R.string.privacy_policy)
                    )
                },
                text = { Text(stringResource(R.string.privacy_policy)) }
            )
            AboutItemCard(
                onCardClick = { context.launchActivity<OssLicensesMenuActivity>() },
                icon = {
                    Icon(
                        Icons.Rounded.Code,
                        contentDescription = stringResource(R.string.open_source_licenses)
                    )
                },
                text = { Text(stringResource(R.string.open_source_licenses)) }
            )
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Text(
                text = LocalContext.current.getVersionName() ?: "",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutItemCard(
    onCardClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier.height(81.dp).fillMaxWidth().padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                Spacer(modifier = Modifier.width(24.dp))
                icon()
                Spacer(modifier = Modifier.width(24.dp))
                text()
            }
        }
    }
}
