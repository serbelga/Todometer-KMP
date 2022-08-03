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

package dev.sergiobelda.todometer.glance

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalGlanceId
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import dev.sergiobelda.todometer.R
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskList
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.glance.SetTaskStateAction.Companion.taskIdKey
import dev.sergiobelda.todometer.glance.SetTaskStateAction.Companion.taskStateKey
import dev.sergiobelda.todometer.ui.MainActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import androidx.glance.appwidget.action.actionStartActivity as actionStartActivityIntent

class ToDometerWidget : GlanceAppWidget(), KoinComponent {

    private val getTaskListSelectedUseCase: GetTaskListSelectedUseCase by inject()

    private val getTaskListSelectedTasksUseCase: GetTaskListSelectedTasksUseCase by inject()

    private val coroutineScope = MainScope()

    private var taskList: TaskList? by mutableStateOf(null)

    private var tasks: List<TaskItem> by mutableStateOf(emptyList())

    private val context by inject<Context>()

    private val openAddTaskDeepLinkIntent = Intent(
        Intent.ACTION_VIEW,
        OPEN_ADD_TASK_DEEP_LINK.toUri(),
        context,
        MainActivity::class.java
    )

    private var glanceId: GlanceId? by mutableStateOf(null)

    init {
        loadData()
    }

    fun loadData() {
        coroutineScope.launch {
            delay(200)

            getTaskListSelectedUseCase().first().doIfSuccess {
                taskList = it
            }.doIfError {
                taskList = null
            }

            getTaskListSelectedTasksUseCase().first().doIfSuccess {
                tasks = it.filter { task -> task.state == TaskState.DOING }
            }

            updateAll(context)
        }
    }

    @Composable
    override fun Content() {
        glanceId = LocalGlanceId.current
        val taskListName: String? =
            if (taskList != null) taskList?.name else context.getString(R.string.default_task_list_name)
        // TODO: Use Loading Progress indicator.
        Box(
            modifier = GlanceModifier.fillMaxSize()
                .background(ImageProvider(R.drawable.todometer_widget_background))
        ) {
            Column(modifier = GlanceModifier.padding(8.dp).fillMaxSize()) {
                Row(
                    modifier = GlanceModifier.fillMaxWidth().padding(start = 8.dp).clickable(
                        onClick = actionStartActivity<MainActivity>()
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = taskListName ?: "",
                        style = TextStyle(
                            color = ColorProvider(R.color.todometer_onSurface),
                            fontSize = 16.sp
                        ),
                        modifier = GlanceModifier.fillMaxWidth().defaultWeight()
                    )
                    Image(
                        ImageProvider(R.drawable.todometer_widget_add_button),
                        modifier = GlanceModifier.clickable(
                            onClick = actionStartActivityIntent(openAddTaskDeepLinkIntent)
                        ),
                        contentDescription = null
                    )
                }
                Spacer(modifier = GlanceModifier.height(12.dp))
                if (tasks.isEmpty()) {
                    Box(
                        modifier = GlanceModifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = context.getString(R.string.no_pending_tasks))
                    }
                } else {
                    LazyColumn {
                        items(tasks) {
                            TaskItem(it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TaskItem(taskItem: TaskItem) {
        val openTaskDeepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "$OPEN_TASK_DEEP_LINK/${taskItem.id}".toUri(),
            context,
            MainActivity::class.java
        )
        Column {
            Row(
                modifier = GlanceModifier.fillMaxWidth()
                    .background(ImageProvider(R.drawable.todometer_widget_card))
                    .clickable(actionStartActivityIntent(openTaskDeepLinkIntent)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val textStyle = if (taskItem.state == TaskState.DONE) {
                    TextStyle(
                        color = ColorProvider(R.color.todometer_onSurface),
                        textDecoration = TextDecoration.LineThrough
                    )
                } else {
                    TextStyle(color = ColorProvider(R.color.todometer_onSurface))
                }
                Text(
                    text = taskItem.title,
                    style = textStyle,
                    modifier = GlanceModifier.padding(start = 8.dp).fillMaxWidth().defaultWeight()
                )
                Image(
                    ImageProvider(if (taskItem.state == TaskState.DONE) R.drawable.ic_round_replay_24 else R.drawable.ic_round_check_24),
                    contentDescription = null,
                    modifier = GlanceModifier.padding(8.dp).clickable(
                        onClick = actionRunCallback<SetTaskStateAction>(
                            actionParametersOf(
                                taskIdKey to taskItem.id,
                                taskStateKey to taskItem.state
                            )
                        )
                    )
                )
            }
            Spacer(modifier = GlanceModifier.height(8.dp))
        }
    }

    companion object {
        private const val OPEN_ADD_TASK_DEEP_LINK: String = "app://open.add.task"
        private const val OPEN_TASK_DEEP_LINK: String = "app://open.task"
    }
}
