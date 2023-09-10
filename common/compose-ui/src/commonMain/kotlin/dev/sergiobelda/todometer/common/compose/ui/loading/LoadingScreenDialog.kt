package dev.sergiobelda.todometer.common.compose.ui.loading

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.resources.MR
import dev.sergiobelda.todometer.common.resources.ToDometerIcons
import dev.sergiobelda.todometer.common.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingScreenDialog(
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            ToDometerIcons.NavigateBefore,
                            contentDescription = stringResource(MR.strings.back)
                        )
                    }
                },
                title = {},
                actions = {}
            )
        },
        content = {
            ContentLoadingProgress()
        }
    )
}
