package com.sergiobelda.todometer.common.datasource

import com.sergiobelda.todometer.common.model.Tag
import kotlinx.coroutines.flow.Flow

interface ITagLocalDatabaseSource {

    fun getTags(): Flow<Result<List<Tag>>>
}
