package dev.sergiobelda.todometer.common.database

import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlin.Boolean
import kotlin.Long
import kotlin.String

public data class SelectTasksByTaskListId(
  public val id: String,
  public val title: String,
  public val state: TaskState,
  public val tasklist_id: String,
  public val tag: Tag,
  public val sync: Boolean,
  public val dueDate: Long?,
  public val checklistItemsDone: Long,
  public val totalChecklistItems: Long,
)
