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

package dev.sergiobelda.todometer.glance

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.text.Text
import dev.sergiobelda.todometer.common.compose.ui.theme.navy
import dev.sergiobelda.todometer.common.sampledata.sampleTasks
import dev.sergiobelda.todometer.ui.MainActivity

class ToDometerWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        Box(modifier = GlanceModifier.cornerRadius(24.dp).background(navy)) {
            Column {
                Button(text = "Button", onClick = actionStartActivity<MainActivity>())
                LazyColumn {
                    items(sampleTasks) {
                        Text(text = it.title)
                    }
                }
            }
        }
    }
}
