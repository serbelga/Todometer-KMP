/*
 * Copyright 2022 Sergio Belda
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

package dev.sergiobelda.todometer.ios

import androidx.compose.foundation.layout.Column
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.ui.main.defaultUIKitMain
import androidx.compose.ui.window.Application
import dev.sergiobelda.todometer.ios.theme.ToDometerAppTheme

fun main() {
    defaultUIKitMain(
        "ToDometer",
        Application("ToDometer") {
            ToDometerAppTheme {
                Column {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = {}) {
                                        Icon(Icons.Rounded.Menu, contentDescription = null)
                                    }
                                },
                                title = {
                                    Text("ToDometer")
                                },
                                actions = {
                                    IconButton(onClick = {}) {
                                        Icon(Icons.Rounded.MoreVert, contentDescription = null)
                                    }
                                }
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = {}) {
                                Icon(Icons.Rounded.Add, contentDescription = null)
                            }
                        }
                    ) { paddingValues ->

                    }
                }
            }
        }
    )
}
