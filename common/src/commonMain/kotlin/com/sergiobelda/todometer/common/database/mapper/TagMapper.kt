package com.sergiobelda.todometer.common.database.mapper

import com.sergiobelda.todometer.TagEntity
import com.sergiobelda.todometer.common.database.DatabaseTypeConverters.colorValueOf
import com.sergiobelda.todometer.common.model.Tag

object TagMapper {

    fun TagEntity.toDomain() = Tag(
        id,
        colorValueOf(color),
        name
    )
}
