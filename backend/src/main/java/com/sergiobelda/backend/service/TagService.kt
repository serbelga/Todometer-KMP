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

package com.sergiobelda.backend.service

import com.sergiobelda.backend.database.dao.ITagDao
import com.sergiobelda.backend.model.NewTag
import com.sergiobelda.backend.model.Tag
import com.sergiobelda.backend.model.toNewTagEntity
import com.sergiobelda.backend.model.toTag
import com.sergiobelda.backend.model.toTagList
import java.util.UUID

class TagService(private val tagDao: ITagDao) : ITagService {

    override suspend fun getTags(): List<Tag> = tagDao.getTags().toTagList()

    override suspend fun getTag(id: String): Tag = tagDao.getTag(UUID.fromString(id)).toTag()

    override suspend fun insertTag(newTag: NewTag): String =
        tagDao.insertTag(newTag.toNewTagEntity()).toString()

    override suspend fun deleteTag(id: String) = tagDao.deleteTag(UUID.fromString(id))
}
