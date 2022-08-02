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

package dev.sergiobelda.todometer.backend

import dev.sergiobelda.todometer.backend.database.AppDatabase
import dev.sergiobelda.todometer.backend.di.configureKoin
import dev.sergiobelda.todometer.backend.plugins.configureSerialization
import dev.sergiobelda.todometer.backend.routing.taskListsRouting
import dev.sergiobelda.todometer.backend.routing.tasksRouting
import dev.sergiobelda.todometer.backend.service.ITaskListService
import dev.sergiobelda.todometer.backend.service.ITaskService
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSerialization()
        configureKoin()

        AppDatabase.init()

        val taskListService: ITaskListService by inject()
        val taskService: ITaskService by inject()

        routing {
            taskListsRouting(taskListService)
            tasksRouting(taskService)
        }
    }.start(wait = true)
}
