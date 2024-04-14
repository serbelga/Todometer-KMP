package dev.sergiobelda.todometer.app

import androidx.compose.runtime.Composable
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

// TODO: Check if it should be on another place
@Composable
expect inline fun <reified T : Any> getViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T
