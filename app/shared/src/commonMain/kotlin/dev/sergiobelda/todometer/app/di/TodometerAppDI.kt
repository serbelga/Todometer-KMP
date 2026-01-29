/*
 * Copyright 2025 Sergio Belda
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

package dev.sergiobelda.todometer.app.di

import dev.sergiobelda.todometer.app.feature.about.di.AboutPresentationDIModule
import dev.sergiobelda.todometer.app.feature.addtask.di.AddTaskPresentationDIModule
import dev.sergiobelda.todometer.app.feature.addtasklist.di.AddTaskListPresentationDIModule
import dev.sergiobelda.todometer.app.feature.edittask.di.EditTaskPresentationDIModule
import dev.sergiobelda.todometer.app.feature.edittasklist.di.EditTaskListPresentationDIModule
import dev.sergiobelda.todometer.app.feature.home.di.HomePresentationDIModule
import dev.sergiobelda.todometer.app.feature.settings.di.SettingsPresentationDIModule
import dev.sergiobelda.todometer.app.feature.taskdetails.di.TaskDetailsPresentationDIModule
import dev.sergiobelda.todometer.common.core.di.TodometerBaseDI
import dev.sergiobelda.todometer.common.di.TodometerDIModule

open class TodometerAppDI : TodometerBaseDI() {
    override val modules: List<TodometerDIModule> =
        super.modules +
            listOf(
                AboutPresentationDIModule,
                AddTaskPresentationDIModule,
                AddTaskListPresentationDIModule,
                EditTaskPresentationDIModule,
                EditTaskListPresentationDIModule,
                HomePresentationDIModule,
                SettingsPresentationDIModule,
                TaskDetailsPresentationDIModule,
            )
}
