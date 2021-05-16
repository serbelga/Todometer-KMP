package com.sergiobelda.todometer.common.repository

import com.sergiobelda.todometer.common.datasource.ITagLocalDatabaseSource
import com.sergiobelda.todometer.common.datasource.Result
import com.sergiobelda.todometer.common.model.Tag
import kotlinx.coroutines.flow.Flow

class TagRepository(
    private val tagLocalDatabaseSource: ITagLocalDatabaseSource
) : ITagRepository {

    override fun getTags(): Flow<Result<List<Tag>>> =
        tagLocalDatabaseSource.getTags()
}
