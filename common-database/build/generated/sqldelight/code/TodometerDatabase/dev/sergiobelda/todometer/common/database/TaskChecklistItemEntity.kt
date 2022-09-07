package dev.sergiobelda.todometer.common.database

import app.cash.sqldelight.ColumnAdapter
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import kotlin.String

public data class TaskChecklistItemEntity(
  public val id: String,
  public val text: String,
  public val task_id: String,
  public val state: TaskChecklistItemState,
) {
  public class Adapter(
    public val stateAdapter: ColumnAdapter<TaskChecklistItemState, String>,
  )
}
