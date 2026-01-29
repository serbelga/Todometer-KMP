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

package dev.sergiobelda.todometer.app.glance

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
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
import dev.sergiobelda.todometer.app.android.R
import dev.sergiobelda.todometer.app.glance.theme.TodometerWidgetTheme
import dev.sergiobelda.todometer.app.ui.main.MainActivity
import dev.sergiobelda.todometer.common.domain.doIfError
import dev.sergiobelda.todometer.common.domain.doIfSuccess
import dev.sergiobelda.todometer.common.domain.model.TaskItem
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.domain.usecase.task.GetTaskListSelectedTasksUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoneUseCase
import dev.sergiobelda.todometer.common.domain.usecase.tasklist.GetTaskListSelectedUseCase
import dev.sergiobelda.todometer.common.resources.TodometerResources
import dev.sergiobelda.todometer.common.ui.task.TaskProgress
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import androidx.glance.appwidget.action.actionStartActivity as actionStartActivityIntent

class TodometerWidget :
    GlanceAppWidget(),
    KoinComponent {
    private val getTaskListSelectedUseCase: GetTaskListSelectedUseCase by inject()

    private val getTaskListSelectedTasksUseCase: GetTaskListSelectedTasksUseCase by inject()

    private val setTaskDoneUseCase: SetTaskDoneUseCase by inject()

    private val setTaskDoingUseCase: SetTaskDoingUseCase by inject()

    private val context by inject<Context>()

    private val openAddTaskDeepLinkIntent =
        Intent(
            Intent.ACTION_VIEW,
            ADD_TASK_DEEP_LINK.toUri(),
            context,
            MainActivity::class.java,
        )

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        provideContent {
            val scope = rememberCoroutineScope()
            var taskListName: String by remember { mutableStateOf("") }
            val taskListSelected by getTaskListSelectedUseCase().collectAsState(null)
            taskListSelected
                ?.doIfSuccess {
                    taskListName = it.name
                }?.doIfError {
                    taskListName = TodometerResources.strings.defaultTaskListName
                }

            val tasksDoing = remember { mutableStateListOf<TaskItem>() }
            val tasksDone = remember { mutableStateListOf<TaskItem>() }
            var taskListProgress by remember { mutableFloatStateOf(0F) }
            val taskListSelectedTasks by getTaskListSelectedTasksUseCase().collectAsState(null)
            taskListSelectedTasks
                ?.doIfSuccess { tasks ->
                    tasksDoing.clear()
                    tasksDoing.addAll(tasks.filter { it.state == TaskState.DOING })

                    tasksDone.clear()
                    tasksDone.addAll(tasks.filter { it.state == TaskState.DONE })

                    taskListProgress = TaskProgress.getTasksDoneProgress(tasks)
                }?.doIfError {
                    tasksDoing.clear()
                    tasksDone.clear()
                    taskListProgress = 0F
                }

            TodometerWidgetTheme {
                TodometerWidgetContent(
                    taskListName = taskListName,
                    taskListProgress = taskListProgress,
                    tasksDoing = tasksDoing,
                    tasksDone = tasksDone,
                    toggleTaskItemState = { id, state ->
                        when (state) {
                            TaskState.DOING -> scope.launch { setTaskDoneUseCase.invoke(id) }
                            TaskState.DONE -> scope.launch { setTaskDoingUseCase.invoke(id) }
                        }
                    },
                )
            }
        }
    }

    @Composable
    private fun TodometerWidgetContent(
        taskListName: String,
        taskListProgress: Float,
        tasksDoing: List<TaskItem>,
        tasksDone: List<TaskItem>,
        toggleTaskItemState: (String, TaskState) -> Unit,
    ) {
        Box(
            modifier =
                GlanceModifier
                    .fillMaxSize()
                    // TODO: Update background color to use GlanceTheme.
                    .background(ImageProvider(R.drawable.todometer_widget_background)),
        ) {
            Column(modifier = GlanceModifier.padding(8.dp).fillMaxSize()) {
                TodometerWidgetHeader(taskListName, taskListProgress)
                Spacer(modifier = GlanceModifier.height(12.dp))
                TodometerWidgetBody(tasksDoing, tasksDone, toggleTaskItemState)
            }
        }
    }

    @Composable
    private fun TodometerWidgetHeader(
        taskListName: String,
        taskListProgress: Float,
    ) {
        // TODO: Use Loading Progress indicator.
        Row(
            modifier =
                GlanceModifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .clickable(
                        onClick = actionStartActivity<MainActivity>(),
                    ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.End,
        ) {
            Column(
                modifier =
                    GlanceModifier
                        .fillMaxWidth()
                        .defaultWeight()
                        .clickable(
                            onClick = actionStartActivity<MainActivity>(),
                        ),
            ) {
                Text(
                    text = taskListName,
                    style =
                        TextStyle(
                            color = GlanceTheme.colors.onSurface,
                            fontSize = 16.sp,
                        ),
                )
                Text(
                    text = TaskProgress.getPercentage(taskListProgress),
                    style =
                        TextStyle(
                            color = GlanceTheme.colors.onSurfaceVariant,
                            fontSize = 16.sp,
                        ),
                )
            }
            // TODO: Use Button when available.
            Image(
                ImageProvider(R.drawable.todometer_widget_add_button),
                modifier =
                    GlanceModifier.clickable(
                        onClick = actionStartActivityIntent(openAddTaskDeepLinkIntent),
                    ),
                contentDescription = null,
            )
        }
    }

    @Composable
    private fun TodometerWidgetBody(
        tasksDoing: List<TaskItem>,
        tasksDone: List<TaskItem>,
        toggleTaskItemState: (String, TaskState) -> Unit,
    ) {
        if (tasksDoing.isEmpty()) {
            Box(
                modifier = GlanceModifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = TodometerResources.strings.noPendingTasks,
                    style = TextStyle(color = GlanceTheme.colors.onBackground),
                )
            }
        } else {
            LazyColumn {
                items(tasksDoing) { taskItem ->
                    TaskItem(
                        taskItem,
                        toggleTaskItemState = {
                            toggleTaskItemState(taskItem.id, taskItem.state)
                        },
                    )
                }
                item {
                    TasksDoneItem(tasksDone.size)
                }
            }
        }
    }

    @Composable
    private fun TaskItem(
        taskItem: TaskItem,
        toggleTaskItemState: () -> Unit,
    ) {
        val openTaskDeepLinkIntent =
            Intent(
                Intent.ACTION_VIEW,
                "$TASK_DETAILS_DEEP_LINK/${taskItem.id}".toUri(),
                context,
                MainActivity::class.java,
            )
        Column {
            // TODO: Use Card with GlanceTheme color surface when available.
            Row(
                modifier =
                    GlanceModifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(ImageProvider(R.drawable.todometer_widget_card))
                        .clickable(actionStartActivityIntent(openTaskDeepLinkIntent)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val textStyle =
                    if (taskItem.state == TaskState.DONE) {
                        TextStyle(
                            color = GlanceTheme.colors.onSurface,
                            textDecoration = TextDecoration.LineThrough,
                        )
                    } else {
                        TextStyle(color = GlanceTheme.colors.onSurface)
                    }
                Text(
                    text = taskItem.title,
                    style = textStyle,
                    modifier =
                        GlanceModifier
                            .padding(start = 8.dp)
                            .fillMaxWidth()
                            .defaultWeight()
                            .clickable(actionStartActivityIntent(openTaskDeepLinkIntent)),
                )
                Image(
                    ImageProvider(
                        if (taskItem.state == TaskState.DONE) {
                            R.drawable.todometer_widget_ic_round_task_alt_24
                        } else {
                            R.drawable.todometer_widget_ic_round_radio_button_unchecked_24
                        },
                    ),
                    contentDescription = null,
                    modifier =
                        GlanceModifier.padding(8.dp).clickable {
                            toggleTaskItemState()
                        },
                    colorFilter = ColorFilter.tint(GlanceTheme.colors.onSurface),
                )
            }
            Spacer(modifier = GlanceModifier.height(8.dp))
        }
    }

    @Composable
    private fun TasksDoneItem(tasksDone: Int) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = TodometerResources.strings.completedTasks(tasksDone),
                style = TextStyle(color = GlanceTheme.colors.onBackground),
            )
        }
    }

    companion object {
        private const val ADD_TASK_DEEP_LINK: String = "app://open.add.task"
        private const val TASK_DETAILS_DEEP_LINK: String = "app://open.task"
    }
}
