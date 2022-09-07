package dev.sergiobelda.todometer.common.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import dev.sergiobelda.todometer.common.domain.model.TaskChecklistItemState
import kotlin.Any
import kotlin.String
import kotlin.Unit

public class TaskChecklistItemEntityQueries(
  driver: SqlDriver,
  private val TaskChecklistItemEntityAdapter: TaskChecklistItemEntity.Adapter,
) : TransacterImpl(driver) {
  public fun <T : Any> selectTaskChecklistItems(task_id: String, mapper: (
    id: String,
    text: String,
    task_id: String,
    state: TaskChecklistItemState,
  ) -> T): Query<T> = SelectTaskChecklistItemsQuery(task_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      TaskChecklistItemEntityAdapter.stateAdapter.decode(cursor.getString(3)!!)
    )
  }

  public fun selectTaskChecklistItems(task_id: String): Query<TaskChecklistItemEntity> =
      selectTaskChecklistItems(task_id) { id, text, task_id_, state ->
    TaskChecklistItemEntity(
      id,
      text,
      task_id_,
      state
    )
  }

  public fun insertTaskChecklistItem(
    id: String,
    text: String,
    task_id: String,
    state: TaskChecklistItemState,
  ): Unit {
    driver.execute(1756211787, """
        |INSERT OR REPLACE INTO TaskChecklistItemEntity
        |(id, text, task_id, state)
        |VALUES (?, ?, ?, ?)
        """.trimMargin(), 4) {
          bindString(0, id)
          bindString(1, text)
          bindString(2, task_id)
          bindString(3, TaskChecklistItemEntityAdapter.stateAdapter.encode(state))
        }
    notifyQueries(1756211787) { emit ->
      emit("TaskChecklistItemEntity")
    }
  }

  public fun updateTaskChecklistItemState(state: TaskChecklistItemState, id: String): Unit {
    driver.execute(-954438890, """
        |UPDATE TaskChecklistItemEntity
        |SET
        |    state = ?
        |WHERE id = ?
        """.trimMargin(), 2) {
          bindString(0, TaskChecklistItemEntityAdapter.stateAdapter.encode(state))
          bindString(1, id)
        }
    notifyQueries(-954438890) { emit ->
      emit("TaskChecklistItemEntity")
    }
  }

  public fun deleteTaskChecklistItem(id: String): Unit {
    driver.execute(-107222631, """DELETE FROM TaskChecklistItemEntity WHERE id = ?""", 1) {
          bindString(0, id)
        }
    notifyQueries(-107222631) { emit ->
      emit("TaskChecklistItemEntity")
    }
  }

  private inner class SelectTaskChecklistItemsQuery<out T : Any>(
    public val task_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    public override fun addListener(listener: Query.Listener): Unit {
      driver.addListener(listener, arrayOf("TaskChecklistItemEntity"))
    }

    public override fun removeListener(listener: Query.Listener): Unit {
      driver.removeListener(listener, arrayOf("TaskChecklistItemEntity"))
    }

    public override fun <R> execute(mapper: (SqlCursor) -> R): QueryResult<R> =
        driver.executeQuery(-1456134389, """
    |SELECT *
    |FROM TaskChecklistItemEntity
    |WHERE task_id = ? ORDER BY state DESC
    """.trimMargin(), mapper, 1) {
      bindString(0, task_id)
    }

    public override fun toString(): String = "TaskChecklistItemEntity.sq:selectTaskChecklistItems"
  }
}
