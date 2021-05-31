package com.sergiobelda.todometer.common.database.mapper

import com.sergiobelda.todometer.TagEntity
import com.sergiobelda.todometer.common.database.DatabaseTypeConverters.colorValueOf
import com.sergiobelda.todometer.common.model.Tag

fun TagEntity.toDomain() = Tag(
    id,
    colorValueOf(color),
    name,
    sync
)

fun Iterable<TagEntity>.toDomain() = this.map {
    it.toDomain()
}
