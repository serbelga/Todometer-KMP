package dev.sergiobelda.todometer.common.database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import kotlin.Unit

public class TaskListEntityQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectTaskList(id: String, mapper: (
    id: String,
    name: String,
    description: String,
    sync: Boolean,
  ) -> T): Query<T> = SelectTaskListQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getBoolean(3)!!
    )
  }

  public fun selectTaskList(id: String): Query<TaskListEntity> = selectTaskList(id) { id_, name,
      description, sync ->
    TaskListEntity(
      id_,
      name,
      description,
      sync
    )
  }

  public fun <T : Any> selectAllTaskLists(mapper: (
    id: String,
    name: String,
    description: String,
    sync: Boolean,
  ) -> T): Query<T> = Query(230386260, arrayOf("TaskListEntity"), driver, "TaskListEntity.sq",
      "selectAllTaskLists", """
  |SELECT *
  |FROM TaskListEntity
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getBoolean(3)!!
    )
  }

  public fun selectAllTaskLists(): Query<TaskListEntity> = selectAllTaskLists { id, name,
      description, sync ->
    TaskListEntity(
      id,
      name,
      description,
      sync
    )
  }

  public fun insertTaskList(
    id: String,
    name: String,
    description: String,
    sync: Boolean,
  ): Unit {
    driver.execute(-1123557147, """
        |INSERT INTO TaskListEntity
        |(id, name, description, sync)
        |VALUES (?, ?, ?, ?)
        """.trimMargin(), 4) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, description)
          bindBoolean(3, sync)
        }
    notifyQueries(-1123557147) { emit ->
      emit("TaskListEntity")
    }
  }

  public fun insertOrReplaceTaskList(
    id: String,
    name: String,
    description: String,
    sync: Boolean,
  ): Unit {
    driver.execute(-1520808110, """
        |INSERT OR REPLACE INTO TaskListEntity
        |(id, name, description, sync)
        |VALUES (?,?, ?, ?)
        """.trimMargin(), 4) {
          bindString(0, id)
          bindString(1, name)
          bindString(2, description)
          bindBoolean(3, sync)
        }
    notifyQueries(-1520808110) { emit ->
      emit("TaskListEntity")
    }
  }

  public fun updateTaskList(
    name: String,
    description: String,
    sync: Boolean,
    id: String,
  ): Unit {
    driver.execute(-1576515851, """
        |UPDATE TaskListEntity
        |SET
        |    name = ?,
        |    description = ?,
        |    sync = ?
        |WHERE id = ?
        """.trimMargin(), 4) {
          bindString(0, name)
          bindString(1, description)
          bindBoolean(2, sync)
          bindString(3, id)
        }
    notifyQueries(-1576515851) { emit ->
      emit("TaskEntity")
      emit("TaskListEntity")
    }
  }

  public fun updateTaskListName(name: String, id: String): Unit {
    driver.execute(1175892768, """
        |UPDATE TaskListEntity
        |SET
        |    name = ?
        |WHERE id = ?
        """.trimMargin(), 2) {
          bindString(0, name)
          bindString(1, id)
        }
    notifyQueries(1175892768) { emit ->
      emit("TaskEntity")
      emit("TaskListEntity")
    }
  }

  public fun deleteTaskList(id: String): Unit {
    driver.execute(-204697129, """
        |DELETE FROM TaskListEntity
        |WHERE id = ?
        """.trimMargin(), 1) {
          bindString(0, id)
        }
    notifyQueries(-204697129) { emit ->
      emit("TaskEntity")
      emit("TaskListEntity")
    }
  }

  private inner class SelectTaskListQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    public override fun addListener(listener: Query.Listener): Unit {
      driver.addListener(listener, arrayOf("TaskListEntity"))
    }

    public override fun removeListener(listener: Query.Listener): Unit {
      driver.removeListener(listener, arrayOf("TaskListEntity"))
    }

    public override fun <R> execute(mapper: (SqlCursor) -> R): QueryResult<R> =
        driver.executeQuery(-1494392792, """
    |SELECT *
    |FROM TaskListEntity
    |WHERE id = ?
    """.trimMargin(), mapper, 1) {
      bindString(0, id)
    }

    public override fun toString(): String = "TaskListEntity.sq:selectTaskList"
  }
}
