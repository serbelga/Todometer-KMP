package com.sergiobelda.todometer.common.repository

import com.sergiobelda.todometer.common.datasource.Result
import com.sergiobelda.todometer.common.localdatasource.ITagLocalDataSource
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.common.remotedatasource.ITagRemoteDataSource
import kotlinx.coroutines.flow.Flow

class TagRepository(
    private val tagLocalDataSource: ITagLocalDataSource,
    private val tagRemoteDataSource: ITagRemoteDataSource
) : ITagRepository {

    override fun getTags(): Flow<Result<List<Tag>>> =
        tagLocalDataSource.getTags()
}
