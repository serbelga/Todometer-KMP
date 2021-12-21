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

package dev.sergiobelda.todometer.backend.routing

import dev.sergiobelda.todometer.backend.model.NewTask
import dev.sergiobelda.todometer.backend.model.TaskState
import dev.sergiobelda.todometer.backend.model.UpdateTaskState
import dev.sergiobelda.todometer.backend.service.ITaskService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route

fun Route.tasksRouting(taskService: ITaskService) {
    route("/v1/tasks") {

        get {
            call.respond(taskService.getTasks(call.parameters["taskListId"]))
        }
        get("/{id}") {
            val taskId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            call.respond(taskService.getTask(taskId))
        }
        post {
            val task = call.receive<NewTask>()
            call.respond(taskService.insertTask(task))
        }
        put("/{id}/state") {
            val taskId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            // TODO Throws ContentTransformationException
            val body = call.receive<UpdateTaskState>()
            // TODO Throws IllegalArgumentException
            val state = enumValueOf<TaskState>(body.state)
            taskService.updateTask(taskId, state)
            call.respond(HttpStatusCode.NoContent)
        }
        delete("/{id}") {
            // TODO respond error if id not present
            val taskId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            taskService.deleteTask(taskId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
