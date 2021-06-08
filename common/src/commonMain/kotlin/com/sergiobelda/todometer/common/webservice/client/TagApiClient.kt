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

package com.sergiobelda.todometer.common.webservice.client

import com.sergiobelda.todometer.common.webservice.TodometerApi
import com.sergiobelda.todometer.common.webservice.TodometerApi.Companion.ENDPOINT_URL
import com.sergiobelda.todometer.common.webservice.TodometerApi.Companion.TAG_PATH
import com.sergiobelda.todometer.common.webservice.TodometerApi.Companion.VERSION_1
import com.sergiobelda.todometer.common.webservice.model.TagApiModel
import io.ktor.client.request.get

class TagApiClient(private val todometerApi: TodometerApi) : ITagApiClient {

    override suspend fun getTags(): List<TagApiModel> =
        todometerApi.client.get(ENDPOINT_URL + VERSION_1 + TAG_PATH)
}
