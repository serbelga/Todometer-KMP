package dev.sergiobelda.todometer.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
actual inline fun <reified T : Any> getViewModel(
    qualifier: Qualifier?,
    noinline parameters: ParametersDefinition?
): T = remember {
    koin.get(
        qualifier = qualifier,
        parameters = parameters
    )
}
