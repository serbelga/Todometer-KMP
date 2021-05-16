package com.sergiobelda.todometer.common.usecase

import com.sergiobelda.todometer.common.datasource.Result
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.common.repository.ITagRepository
import kotlinx.coroutines.flow.Flow

class GetTagsUseCase(private val tagRepository: ITagRepository) {

    operator fun invoke(): Flow<Result<List<Tag>>> =
        tagRepository.getTags()
}
