package dev.sergiobelda.todometer.common.`data`.database

import com.squareup.sqldelight.ColumnAdapter
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlin.Boolean
import kotlin.String

public data class TaskEntity(
  public val id: String,
  public val title: String,
  public val description: String?,
  public val state: TaskState,
  public val tasklist_id: String,
  public val tag: Tag,
  public val sync: Boolean
) {
  public override fun toString(): String = """
  |TaskEntity [
  |  id: $id
  |  title: $title
  |  description: $description
  |  state: $state
  |  tasklist_id: $tasklist_id
  |  tag: $tag
  |  sync: $sync
  |]
  """.trimMargin()

  public class Adapter(
    public val stateAdapter: ColumnAdapter<TaskState, String>,
    public val tagAdapter: ColumnAdapter<Tag, String>
  )
}
