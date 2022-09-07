package dev.sergiobelda.todometer.common.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlin.Any
import kotlin.Boolean
import kotlin.Long
import kotlin.String
import kotlin.Unit

public class TaskEntityQueries(
  driver: SqlDriver,
  private val TaskEntityAdapter: TaskEntity.Adapter,
) : TransacterImpl(driver) {
  public fun <T : Any> selectTask(id: String, mapper: (
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean,
    dueDate: Long?,
  ) -> T): Query<T> = SelectTaskQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      TaskEntityAdapter.stateAdapter.decode(cursor.getString(3)!!),
      cursor.getString(4)!!,
      TaskEntityAdapter.tagAdapter.decode(cursor.getString(5)!!),
      cursor.getBoolean(6)!!,
      cursor.getLong(7)
    )
  }

  public fun selectTask(id: String): Query<TaskEntity> = selectTask(id) { id_, title, description,
      state, tasklist_id, tag, sync, dueDate ->
    TaskEntity(
      id_,
      title,
      description,
      state,
      tasklist_id,
      tag,
      sync,
      dueDate
    )
  }

  public fun <T : Any> selectTasksByTaskListId(tasklist_id: String, mapper: (
    id: String,
    title: String,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean,
    dueDate: Long?,
    checklistItemsDone: Long,
    totalChecklistItems: Long,
  ) -> T): Query<T> = SelectTasksByTaskListIdQuery(tasklist_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      TaskEntityAdapter.stateAdapter.decode(cursor.getString(2)!!),
      cursor.getString(3)!!,
      TaskEntityAdapter.tagAdapter.decode(cursor.getString(4)!!),
      cursor.getBoolean(5)!!,
      cursor.getLong(6),
      cursor.getLong(7)!!,
      cursor.getLong(8)!!
    )
  }

  public fun selectTasksByTaskListId(tasklist_id: String): Query<SelectTasksByTaskListId> =
      selectTasksByTaskListId(tasklist_id) { id, title, state, tasklist_id_, tag, sync, dueDate,
      checklistItemsDone, totalChecklistItems ->
    SelectTasksByTaskListId(
      id,
      title,
      state,
      tasklist_id_,
      tag,
      sync,
      dueDate,
      checklistItemsDone,
      totalChecklistItems
    )
  }

  public fun <T : Any> selectAllTasks(mapper: (
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean,
    dueDate: Long?,
  ) -> T): Query<T> = Query(-575106796, arrayOf("TaskEntity"), driver, "TaskEntity.sq",
      "selectAllTasks", """
  |SELECT *
  |FROM TaskEntity
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      TaskEntityAdapter.stateAdapter.decode(cursor.getString(3)!!),
      cursor.getString(4)!!,
      TaskEntityAdapter.tagAdapter.decode(cursor.getString(5)!!),
      cursor.getBoolean(6)!!,
      cursor.getLong(7)
    )
  }

  public fun selectAllTasks(): Query<TaskEntity> = selectAllTasks { id, title, description, state,
      tasklist_id, tag, sync, dueDate ->
    TaskEntity(
      id,
      title,
      description,
      state,
      tasklist_id,
      tag,
      sync,
      dueDate
    )
  }

  public fun insertOrReplaceTask(
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean,
    dueDate: Long?,
  ): Unit {
    driver.execute(-464233582, """
        |INSERT OR REPLACE INTO TaskEntity
        |(id, title, description, state, tasklist_id, tag, sync, dueDate)
        |VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """.trimMargin(), 8) {
          bindString(0, id)
          bindString(1, title)
          bindString(2, description)
          bindString(3, TaskEntityAdapter.stateAdapter.encode(state))
          bindString(4, tasklist_id)
          bindString(5, TaskEntityAdapter.tagAdapter.encode(tag))
          bindBoolean(6, sync)
          bindLong(7, dueDate)
        }
    notifyQueries(-464233582) { emit ->
      emit("TaskEntity")
    }
  }

  public fun updateTask(
    title: String,
    description: String?,
    tag: Tag,
    dueDate: Long?,
    id: String,
  ): Unit {
    driver.execute(-1945855751, """
        |UPDATE TaskEntity
        |SET
        |    title = ?,
        |    description = ?,
        |    tag = ?,
        |    dueDate = ?
        |WHERE id = ?
        """.trimMargin(), 5) {
          bindString(0, title)
          bindString(1, description)
          bindString(2, TaskEntityAdapter.tagAdapter.encode(tag))
          bindLong(3, dueDate)
          bindString(4, id)
        }
    notifyQueries(-1945855751) { emit ->
      emit("TaskChecklistItemEntity")
      emit("TaskEntity")
    }
  }

  public fun updateTaskSync(sync: Boolean, id: String): Unit {
    driver.execute(1440023348, """
        |UPDATE TaskEntity
        |SET
        |    sync = ?
        |WHERE id = ?
        """.trimMargin(), 2) {
          bindBoolean(0, sync)
          bindString(1, id)
        }
    notifyQueries(1440023348) { emit ->
      emit("TaskChecklistItemEntity")
      emit("TaskEntity")
    }
  }

  public fun updateTaskState(state: TaskState, id: String): Unit {
    driver.execute(1690890008, """
        |UPDATE TaskEntity
        |SET
        |    state = ?
        |WHERE id = ?
        """.trimMargin(), 2) {
          bindString(0, TaskEntityAdapter.stateAdapter.encode(state))
          bindString(1, id)
        }
    notifyQueries(1690890008) { emit ->
      emit("TaskChecklistItemEntity")
      emit("TaskEntity")
    }
  }

  public fun deleteTask(id: String): Unit {
    driver.execute(114664667, """
        |DELETE FROM TaskEntity
        |WHERE id = ?
        """.trimMargin(), 1) {
          bindString(0, id)
        }
    notifyQueries(114664667) { emit ->
      emit("TaskChecklistItemEntity")
      emit("TaskEntity")
    }
  }

  private inner class SelectTaskQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    public override fun addListener(listener: Query.Listener): Unit {
      driver.addListener(listener, arrayOf("TaskEntity"))
    }

    public override fun removeListener(listener: Query.Listener): Unit {
      driver.removeListener(listener, arrayOf("TaskEntity"))
    }

    public override fun <R> execute(mapper: (SqlCursor) -> R): QueryResult<R> =
        driver.executeQuery(989086636, """
    |SELECT *
    |FROM TaskEntity
    |WHERE id = ?
    """.trimMargin(), mapper, 1) {
      bindString(0, id)
    }

    public override fun toString(): String = "TaskEntity.sq:selectTask"
  }

  private inner class SelectTasksByTaskListIdQuery<out T : Any>(
    public val tasklist_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    public override fun addListener(listener: Query.Listener): Unit {
      driver.addListener(listener, arrayOf("TaskEntity", "TaskChecklistItemEntity"))
    }

    public override fun removeListener(listener: Query.Listener): Unit {
      driver.removeListener(listener, arrayOf("TaskEntity", "TaskChecklistItemEntity"))
    }

    public override fun <R> execute(mapper: (SqlCursor) -> R): QueryResult<R> =
        driver.executeQuery(590098396, """
    |SELECT t.id, t.title, t.state, t.tasklist_id, t.tag, t.sync, t.dueDate, COUNT(CASE WHEN c.state = 'CHECKED' THEN 1 ELSE NULL END) AS 'checklistItemsDone', COUNT(c.id) AS 'totalChecklistItems'
    |FROM TaskEntity t LEFT OUTER JOIN TaskChecklistItemEntity c ON t.id = c.task_id
    |WHERE t.tasklist_id = ? GROUP BY t.id
    """.trimMargin(), mapper, 1) {
      bindString(0, tasklist_id)
    }

    public override fun toString(): String = "TaskEntity.sq:selectTasksByTaskListId"
  }
}
