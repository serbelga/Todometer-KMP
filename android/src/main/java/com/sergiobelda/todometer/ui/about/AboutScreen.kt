package com.sergiobelda.todometer.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sergiobelda.todometer.android.R
import com.sergiobelda.todometer.compose.ui.theme.TodometerTypography

@Composable
fun AboutScreen(navigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                elevation = 0.dp,
                title = {}
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            ToDometerLogo()
            Spacer(modifier = Modifier.height(72.dp))
            AboutItemCard(
                icon = {
                    Icon(
                        painterResource(R.drawable.ic_github_24),
                        contentDescription = stringResource(R.string.github)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.github)
                    )
                }
            ) {

            }
            AboutItemCard(
                icon = {
                    Icon(
                        Icons.Rounded.Description,
                        contentDescription = stringResource(R.string.privacy_policy)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.privacy_policy)
                    )
                }
            ) {

            }
            AboutItemCard(
                icon = {
                    Icon(
                        Icons.Rounded.Code,
                        contentDescription = stringResource(R.string.open_source_licenses)
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.open_source_licenses)
                    )
                }
            ) {

            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Text(
                text = "2.0.0",
                style = TodometerTypography.overline,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun AboutItemCard(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    onCardClick: () -> Unit
) {
    Card(modifier = Modifier.height(81.dp).fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.clickable { onCardClick() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(24.dp))
            icon()
            Spacer(modifier = Modifier.width(24.dp))
            text()
        }
    }
}

@Composable
fun ToDometerLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painterResource(id = R.drawable.isotype), null)
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

