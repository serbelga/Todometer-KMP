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

package com.sergiobelda.todometer.common.localdatasource

import com.sergiobelda.todometer.common.database.dao.ITagDao
import com.sergiobelda.todometer.common.database.mapper.toDomain
import com.sergiobelda.todometer.common.datasource.Result
import com.sergiobelda.todometer.common.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagLocalDataSource(
    private val tagDao: ITagDao
) : ITagLocalDataSource {

    override fun getTags(): Flow<Result<List<Tag>>> =
        tagDao.getTags().map { list ->
            Result.Success(list.toDomain())
        }
}
