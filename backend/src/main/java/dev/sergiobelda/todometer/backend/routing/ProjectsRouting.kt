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

import dev.sergiobelda.todometer.backend.model.NewTaskList
import dev.sergiobelda.todometer.backend.model.TaskList
import dev.sergiobelda.todometer.backend.service.ITaskListService
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

fun Route.taskListsRouting(taskListService: ITaskListService) {
    route("/v1/tasklists") {

        // TODO: Handle exceptions
        get {
            call.respond(taskListService.getTaskLists())
        }
        get("/{id}") {
            // TODO respond error if id not present
            val taskListId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            call.respond(taskListService.getTaskList(taskListId))
        }
        post {
            val taskList = call.receive<NewTaskList>()
            call.respond(taskListService.insertTaskList(taskList))
        }
        put {
            val taskList = call.receive<TaskList>()
            taskListService.updateTaskList(taskList)
            call.respond(taskList)
        }
        delete("/{id}") {
            // TODO respond error if id not present
            val taskListId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            taskListService.deleteTaskList(taskListId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
