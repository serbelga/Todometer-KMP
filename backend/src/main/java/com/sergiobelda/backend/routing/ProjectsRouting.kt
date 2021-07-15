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

import com.sergiobelda.backend.model.NewProject
import com.sergiobelda.backend.model.Project
import com.sergiobelda.backend.service.IProjectService
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

fun Route.projectsRouting(projectService: IProjectService) {
    route("/v1/projects") {

        // TODO: Handle exceptions
        get {
            call.respond(projectService.getProjects())
        }
        get("/{id}") {
            // TODO respond error if id not present
            val projectId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            call.respond(projectService.getProject(projectId))
        }
        post {
            val project = call.receive<NewProject>()
            call.respond(projectService.insertProject(project))
        }
        put {
            val project = call.receive<Project>()
            projectService.updateProject(project)
            call.respond(project)
        }
        delete("/{id}") {
            // TODO respond error if id not present
            val projectId = call.parameters["id"] ?: throw IllegalStateException("Must provide id")
            projectService.deleteProject(projectId)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
