package dev.sergiobelda.todometer.common.compose.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import dev.sergiobelda.todometer.common.compose.ui.designsystem.components.ToDometerAlertDialog
import dev.sergiobelda.todometer.common.compose.ui.preferences.themeName
import dev.sergiobelda.todometer.common.domain.preference.AppTheme
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.stringResource

@Composable
fun ChooseThemeAlertDialog(
    currentTheme: AppTheme,
    onDismissRequest: () -> Unit,
    onChooseThemeClick: (theme: AppTheme) -> Unit
) {
    var themeSelected by remember { mutableStateOf(currentTheme) }
    ToDometerAlertDialog(
        title = {
            Text(
                text = stringResource(resource = MR.strings.choose_theme),
                modifier = Modifier.padding(16.dp)
            )
        },
        onDismissRequest = onDismissRequest,
        text = {
            LazyColumn {
                enumValues<AppTheme>().forEach { appTheme ->
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = themeSelected == appTheme,
                                    onClick = { themeSelected = appTheme },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp)
                        ) {
                            RadioButton(
                                selected = themeSelected == appTheme,
                                onClick = { themeSelected = appTheme }
                            )
                            Text(
                                text = appTheme.themeName(),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onChooseThemeClick(themeSelected)
                    onDismissRequest()
                }
            ) {
                Text(stringResource(resource = MR.strings.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(resource = MR.strings.cancel))
            }
        }
    )
}
