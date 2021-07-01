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

package com.sergiobelda.backend

import com.sergiobelda.backend.database.AppDatabase
import com.sergiobelda.backend.di.configureKoin
import com.sergiobelda.backend.plugins.configureSerialization
import com.sergiobelda.backend.routing.projectsRouting
import com.sergiobelda.backend.routing.tagsRouting
import com.sergiobelda.backend.routing.tasksRouting
import com.sergiobelda.backend.service.IProjectService
import com.sergiobelda.backend.service.ITagService
import com.sergiobelda.backend.service.ITaskService
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.inject

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSerialization()
        configureKoin()

        AppDatabase.init()

        val projectService: IProjectService by inject()
        val tagService: ITagService by inject()
        val taskService: ITaskService by inject()

        routing {
            projectsRouting(projectService)
            tagsRouting(tagService)
            tasksRouting(taskService)
        }
    }.start(wait = true)
}
