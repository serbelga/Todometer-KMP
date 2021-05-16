package com.sergiobelda.todometer.common.datasource

import com.sergiobelda.todometer.common.database.dao.ITagDao
import com.sergiobelda.todometer.common.database.mapper.TagMapper.toDomain
import com.sergiobelda.todometer.common.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagLocalDatabaseSource(
    private val tagDao: ITagDao
) : ITagLocalDatabaseSource {

    override fun getTags(): Flow<Result<List<Tag>>> =
        tagDao.getTags().map { list ->
            Result.Success(list.map { it.toDomain() })
        }
}
