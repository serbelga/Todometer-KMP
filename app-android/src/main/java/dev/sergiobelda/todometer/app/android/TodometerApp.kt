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

package dev.sergiobelda.todometer.app.android

import dev.sergiobelda.todometer.app.android.di.mainViewModelModule
import dev.sergiobelda.todometer.app.feature.addtask.di.addTaskViewModelModule
import dev.sergiobelda.todometer.app.feature.addtasklist.di.addTaskListViewModelModule
import dev.sergiobelda.todometer.app.feature.edittask.di.editTaskViewModelModule
import dev.sergiobelda.todometer.app.feature.edittasklist.di.editTaskListViewModelModule
import dev.sergiobelda.todometer.app.feature.home.di.homeViewModelModule
import dev.sergiobelda.todometer.app.feature.settings.di.settingsViewModelModule
import dev.sergiobelda.todometer.app.feature.taskdetails.di.taskDetailsViewModelModule
import dev.sergiobelda.todometer.common.core.app.TodometerBaseApp
import dev.sergiobelda.todometer.common.core.di.startAppDI
import org.koin.android.ext.koin.androidContext

class TodometerApp : TodometerBaseApp() {

    override fun onCreate() {
        super.onCreate()
        startAppDI {
            androidContext(this@TodometerApp)
            modules(
                mainViewModelModule +
                    addTaskViewModelModule +
                    addTaskListViewModelModule +
                    editTaskViewModelModule +
                    editTaskListViewModelModule +
                    homeViewModelModule +
                    settingsViewModelModule +
                    taskDetailsViewModelModule
            )
        }
    }
}
