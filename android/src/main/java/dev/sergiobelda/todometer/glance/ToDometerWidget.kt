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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import dev.sergiobelda.todometer.common.compose.ui.theme.navy
import dev.sergiobelda.todometer.common.data.doIfSuccess
import dev.sergiobelda.todometer.common.model.Task
import dev.sergiobelda.todometer.common.usecase.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.ui.MainActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ToDometerWidget : GlanceAppWidget(), KoinComponent {

    private val getTaskListSelectedTasksUseCase: GetTaskListSelectedTasksUseCase by inject()

    private val coroutineScope = MainScope()

    private var list by mutableStateOf<List<Task>>(emptyList())

    init {
        coroutineScope.launch {
            getTaskListSelectedTasksUseCase().first().doIfSuccess {
                list = it
            }
        }
    }

    @Composable
    override fun Content() {
        Box(modifier = GlanceModifier.cornerRadius(16.dp).fillMaxSize().background(navy)) {
            Column(modifier = GlanceModifier.padding(8.dp).fillMaxSize()) {
                Button(text = "Add task", onClick = actionStartActivity<MainActivity>())
                if (list.isEmpty()) {
                    Box(
                        modifier = GlanceModifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "There are any task")
                    }
                } else {
                    LazyColumn {
                        items(list) {
                            TaskItem(it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TaskItem(task: Task) {
        Column(modifier = GlanceModifier.clickable(onClick = actionStartActivity<MainActivity>())) {
            Box(
                modifier = GlanceModifier.cornerRadius(6.dp).fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.12f))
                    .padding(12.dp)
            ) {
                Text(text = task.title, style = TextStyle(color = ColorProvider(Color.White)))
            }
            Spacer(modifier = GlanceModifier.height(8.dp))
        }
    }
}
