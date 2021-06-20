package com.sergiobelda.todometer.common.database.mapper

import com.sergiobelda.todometer.TagEntity
import com.sergiobelda.todometer.common.model.Tag
import com.sergiobelda.todometer.common.model.TypeConverters.colorValueOf

fun TagEntity.toDomain() = Tag(
    id,
    colorValueOf(color),
    name,
    sync
)

fun Iterable<TagEntity>.toDomain() = this.map {
    it.toDomain()
}
