package dev.sergiobelda.todometer.common.`data`.database

import kotlin.Boolean
import kotlin.String

public data class TaskListEntity(
  public val id: String,
  public val name: String,
  public val description: String,
  public val sync: Boolean
) {
  public override fun toString(): String = """
  |TaskListEntity [
  |  id: $id
  |  name: $name
  |  description: $description
  |  sync: $sync
  |]
  """.trimMargin()
}
