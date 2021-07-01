package com.sergiobelda.backend.database.dao

import com.sergiobelda.backend.database.entity.NewTagEntity
import com.sergiobelda.backend.database.entity.TagEntity
import com.sergiobelda.backend.database.mapper.toTagEntity
import com.sergiobelda.backend.database.mapper.toTagEntityList
import com.sergiobelda.backend.database.table.TagTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.UUID

class TagDao : ITagDao {

    override suspend fun getTag(id: UUID): TagEntity = newSuspendedTransaction {
        TagTable.select { TagTable.id eq id }.single().toTagEntity()
    }

    override suspend fun getTags(): List<TagEntity> = newSuspendedTransaction {
        TagTable.selectAll().toTagEntityList()
    }

    override suspend fun insertTag(tag: NewTagEntity): UUID =
        newSuspendedTransaction {
            TagTable.replace {
                tag.id?.let { uuid ->
                    it[id] = uuid
                }
                it[color] = tag.color
                it[name] = tag.name
            } get TagTable.id
        }

    override suspend fun deleteTag(id: UUID) {
        newSuspendedTransaction {
            TagTable.deleteWhere { TagTable.id eq id }
        }
    }
}
