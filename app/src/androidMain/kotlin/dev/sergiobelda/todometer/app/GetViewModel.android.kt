package dev.sergiobelda.todometer.app

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
actual inline fun <reified T : Any> getViewModel(
    qualifier: Qualifier?,
    noinline parameters: ParametersDefinition?
): T = koinViewModel(
    qualifier = qualifier,
    parameters = parameters
)
