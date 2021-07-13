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

package com.sergiobelda.backend.routing

import com.sergiobelda.backend.model.NewTask
import com.sergiobelda.backend.service.ITaskService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.tasksRouting(taskService: ITaskService) {
    route("/v1/tasks") {

        get {
            call.respond(taskService.getTasks(call.parameters["projectId"]))
        }
        get("/{id}") {
            val taskId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            call.respond(taskService.getTask(taskId))
        }
        post {
            val task = call.receive<NewTask>()
            call.respond(taskService.insertTask(task))
        }
        delete("/{id}") {
            // TODO respond error if id not present
            val taskId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            taskService.deleteTask(taskId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
