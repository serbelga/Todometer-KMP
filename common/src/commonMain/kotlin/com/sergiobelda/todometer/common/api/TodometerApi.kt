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

package com.sergiobelda.todometer.common.api

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json

internal expect val ApplicationDispatcher: CoroutineDispatcher

class TodometerApi {
    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }

    companion object {

        private const val SCHEME = "http"
        const val HOST = "192.168.0.11"
        const val PORT = "8080"
        const val ENDPOINT_URL = "$SCHEME://$HOST:$PORT"
        const val VERSION_1 = "/v1"
        const val PROJECT_PATH = "/projects"
        const val TASK_PATH = "/tasks"
        const val TAG_PATH = "/tags"
    }
}
