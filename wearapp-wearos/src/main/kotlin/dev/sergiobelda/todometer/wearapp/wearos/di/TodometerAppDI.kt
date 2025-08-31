/*
 * Copyright 2024 Sergio Belda
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

package dev.sergiobelda.todometer.wearapp.wearos.di

import androidx.compose.runtime.Composable
import dev.sergiobelda.todometer.common.core.di.dataModules
import dev.sergiobelda.todometer.common.core.di.domainModules
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
fun TodometerAppDI(
    appDeclaration: KoinAppDeclaration = {},
    content: @Composable () -> Unit,
) = KoinApplication(
    application = {
        modules(
            dataModules +
                domainModules +
                presentationModules,
        )
        appDeclaration()
    },
    content = content,
)
