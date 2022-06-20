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
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import dev.sergiobelda.todometer.common.domain.model.TaskState
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoingUseCase
import dev.sergiobelda.todometer.common.domain.usecase.task.SetTaskDoneUseCase
import org.koin.java.KoinJavaComponent.inject

class SetTaskStateAction : ActionCallback {

    private val setTaskDoneUseCase: SetTaskDoneUseCase by inject(SetTaskDoneUseCase::class.java)

    private val setTaskDoingUseCase: SetTaskDoingUseCase by inject(SetTaskDoingUseCase::class.java)

    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val taskId: String = parameters[taskIdKey] ?: ""
        parameters[taskStateKey]?.let { state ->
            when (state) {
                TaskState.DOING -> setTaskDoneUseCase.invoke(taskId)
                TaskState.DONE -> setTaskDoingUseCase.invoke(taskId)
            }
            ToDometerWidget().loadData()
        }
    }

    companion object {
        private const val TASK_ID = "taskId"
        private const val TASK_STATE = "taskState"
        val taskIdKey = ActionParameters.Key<String>(TASK_ID)
        val taskStateKey = ActionParameters.Key<TaskState>(TASK_STATE)
    }
}
