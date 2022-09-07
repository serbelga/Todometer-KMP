package dev.sergiobelda.todometer.common.database

import kotlin.Boolean
import kotlin.String

public data class TaskListEntity(
  public val id: String,
  public val name: String,
  public val description: String,
  public val sync: Boolean,
)
