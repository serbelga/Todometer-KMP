package dev.sergiobelda.todometer.common.`data`.database.commondata

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.`internal`.copyOnWriteList
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import dev.sergiobelda.todometer.common.`data`.database.TaskEntity
import dev.sergiobelda.todometer.common.`data`.database.TaskListEntity
import dev.sergiobelda.todometer.common.`data`.database.TodometerDatabase
import dev.sergiobelda.todometer.common.`data`.database.TodometerQueries
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.collections.MutableList
import kotlin.reflect.KClass

internal val KClass<TodometerDatabase>.schema: SqlDriver.Schema
  get() = TodometerDatabaseImpl.Schema

internal fun KClass<TodometerDatabase>.newInstance(driver: SqlDriver,
    TaskEntityAdapter: TaskEntity.Adapter): TodometerDatabase = TodometerDatabaseImpl(driver,
    TaskEntityAdapter)

private class TodometerDatabaseImpl(
  driver: SqlDriver,
  internal val TaskEntityAdapter: TaskEntity.Adapter
) : TransacterImpl(driver), TodometerDatabase {
  public override val todometerQueries: TodometerQueriesImpl = TodometerQueriesImpl(this, driver)

  public object Schema : SqlDriver.Schema {
    public override val version: Int
      get() = 1

    public override fun create(driver: SqlDriver): Unit {
      driver.execute(null, """
          |CREATE TABLE TaskListEntity (
          |    id TEXT PRIMARY KEY NOT NULL,
          |    name TEXT NOT NULL,
          |    description TEXT NOT NULL,
          |    sync INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE TaskEntity (
          |    id TEXT PRIMARY KEY NOT NULL,
          |    title TEXT NOT NULL,
          |    description TEXT,
          |    state TEXT NOT NULL,
          |    tasklist_id TEXT NOT NULL,
          |    tag TEXT NOT NULL,
          |    sync INTEGER NOT NULL DEFAULT 0,
          |    FOREIGN KEY(tasklist_id) REFERENCES TaskListEntity(id) ON UPDATE CASCADE ON DELETE CASCADE
          |)
          """.trimMargin(), 0)
    }

    public override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ): Unit {
    }
  }
}

private class TodometerQueriesImpl(
  private val database: TodometerDatabaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), TodometerQueries {
  internal val selectTask: MutableList<Query<*>> = copyOnWriteList()

  internal val selectTasksByTaskListId: MutableList<Query<*>> = copyOnWriteList()

  internal val selectAllTasks: MutableList<Query<*>> = copyOnWriteList()

  internal val selectTaskList: MutableList<Query<*>> = copyOnWriteList()

  internal val selectAllTaskLists: MutableList<Query<*>> = copyOnWriteList()

  public override fun <T : Any> selectTask(id: String, mapper: (
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean
  ) -> T): Query<T> = SelectTaskQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      database.TaskEntityAdapter.stateAdapter.decode(cursor.getString(3)!!),
      cursor.getString(4)!!,
      database.TaskEntityAdapter.tagAdapter.decode(cursor.getString(5)!!),
      cursor.getLong(6)!! == 1L
    )
  }

  public override fun selectTask(id: String): Query<TaskEntity> = selectTask(id) { id_, title,
      description, state, tasklist_id, tag, sync ->
    TaskEntity(
      id_,
      title,
      description,
      state,
      tasklist_id,
      tag,
      sync
    )
  }

  public override fun <T : Any> selectTasksByTaskListId(tasklist_id: String, mapper: (
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean
  ) -> T): Query<T> = SelectTasksByTaskListIdQuery(tasklist_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      database.TaskEntityAdapter.stateAdapter.decode(cursor.getString(3)!!),
      cursor.getString(4)!!,
      database.TaskEntityAdapter.tagAdapter.decode(cursor.getString(5)!!),
      cursor.getLong(6)!! == 1L
    )
  }

  public override fun selectTasksByTaskListId(tasklist_id: String): Query<TaskEntity> =
      selectTasksByTaskListId(tasklist_id) { id, title, description, state, tasklist_id_, tag,
      sync ->
    TaskEntity(
      id,
      title,
      description,
      state,
      tasklist_id_,
      tag,
      sync
    )
  }

  public override fun <T : Any> selectAllTasks(mapper: (
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean
  ) -> T): Query<T> = Query(549188341, selectAllTasks, driver, "Todometer.sq", "selectAllTasks", """
  |SELECT *
  |FROM TaskEntity
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2),
      database.TaskEntityAdapter.stateAdapter.decode(cursor.getString(3)!!),
      cursor.getString(4)!!,
      database.TaskEntityAdapter.tagAdapter.decode(cursor.getString(5)!!),
      cursor.getLong(6)!! == 1L
    )
  }

  public override fun selectAllTasks(): Query<TaskEntity> = selectAllTasks { id, title, description,
      state, tasklist_id, tag, sync ->
    TaskEntity(
      id,
      title,
      description,
      state,
      tasklist_id,
      tag,
      sync
    )
  }

  public override fun <T : Any> selectTaskList(id: String, mapper: (
    id: String,
    name: String,
    description: String,
    sync: Boolean
  ) -> T): Query<T> = SelectTaskListQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!! == 1L
    )
  }

  public override fun selectTaskList(id: String): Query<TaskListEntity> = selectTaskList(id) { id_,
      name, description, sync ->
    TaskListEntity(
      id_,
      name,
      description,
      sync
    )
  }

  public override fun <T : Any> selectAllTaskLists(mapper: (
    id: String,
    name: String,
    description: String,
    sync: Boolean
  ) -> T): Query<T> = Query(-1459923721, selectAllTaskLists, driver, "Todometer.sq",
      "selectAllTaskLists", """
  |SELECT *
  |FROM TaskListEntity
  """.trimMargin()) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!! == 1L
    )
  }

  public override fun selectAllTaskLists(): Query<TaskListEntity> = selectAllTaskLists { id, name,
      description, sync ->
    TaskListEntity(
      id,
      name,
      description,
      sync
    )
  }

  public override fun insertOrReplaceTask(
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean
  ): Unit {
    driver.execute(288882257, """
    |INSERT OR REPLACE INTO TaskEntity
    |(id, title, description, state, tasklist_id, tag, sync)
    |VALUES (?, ?, ?, ?, ?, ?, ?)
    """.trimMargin(), 7) {
      bindString(1, id)
      bindString(2, title)
      bindString(3, description)
      bindString(4, database.TaskEntityAdapter.stateAdapter.encode(state))
      bindString(5, tasklist_id)
      bindString(6, database.TaskEntityAdapter.tagAdapter.encode(tag))
      bindLong(7, if (sync) 1L else 0L)
    }
    notifyQueries(288882257, {database.todometerQueries.selectTask +
        database.todometerQueries.selectTasksByTaskListId +
        database.todometerQueries.selectAllTasks})
  }

  public override fun updateTask(
    title: String,
    description: String?,
    tag: Tag,
    id: String
  ): Unit {
    driver.execute(922620762, """
    |UPDATE TaskEntity
    |SET
    |    title = ?,
    |    description = ?,
    |    tag = ?
    |WHERE id = ?
    """.trimMargin(), 4) {
      bindString(1, title)
      bindString(2, description)
      bindString(3, database.TaskEntityAdapter.tagAdapter.encode(tag))
      bindString(4, id)
    }
    notifyQueries(922620762, {database.todometerQueries.selectTask +
        database.todometerQueries.selectTasksByTaskListId +
        database.todometerQueries.selectAllTasks})
  }

  public override fun updateTaskSync(sync: Boolean, id: String): Unit {
    driver.execute(-1730648811, """
    |UPDATE TaskEntity
    |SET
    |    sync = ?
    |WHERE id = ?
    """.trimMargin(), 2) {
      bindLong(1, if (sync) 1L else 0L)
      bindString(2, id)
    }
    notifyQueries(-1730648811, {database.todometerQueries.selectTask +
        database.todometerQueries.selectTasksByTaskListId +
        database.todometerQueries.selectAllTasks})
  }

  public override fun updateTaskState(state: TaskState, id: String): Unit {
    driver.execute(-2110666409, """
    |UPDATE TaskEntity
    |SET
    |    state = ?
    |WHERE id = ?
    """.trimMargin(), 2) {
      bindString(1, database.TaskEntityAdapter.stateAdapter.encode(state))
      bindString(2, id)
    }
    notifyQueries(-2110666409, {database.todometerQueries.selectTask +
        database.todometerQueries.selectTasksByTaskListId +
        database.todometerQueries.selectAllTasks})
  }

  public override fun deleteTask(id: String): Unit {
    driver.execute(-1311826116, """
    |DELETE FROM TaskEntity
    |WHERE id = ?
    """.trimMargin(), 1) {
      bindString(1, id)
    }
    notifyQueries(-1311826116, {database.todometerQueries.selectTask +
        database.todometerQueries.selectTasksByTaskListId +
        database.todometerQueries.selectAllTasks})
  }

  public override fun insertTaskList(
    id: String,
    name: String,
    description: String,
    sync: Boolean
  ): Unit {
    driver.execute(-1277913848, """
    |INSERT INTO TaskListEntity
    |(id, name, description, sync)
    |VALUES (?, ?, ?, ?)
    """.trimMargin(), 4) {
      bindString(1, id)
      bindString(2, name)
      bindString(3, description)
      bindLong(4, if (sync) 1L else 0L)
    }
    notifyQueries(-1277913848, {database.todometerQueries.selectTaskList +
        database.todometerQueries.selectAllTaskLists})
  }

  public override fun insertOrReplaceTaskList(
    id: String,
    name: String,
    description: String,
    sync: Boolean
  ): Unit {
    driver.execute(-1650290033, """
    |INSERT OR REPLACE INTO TaskListEntity
    |(id, name, description, sync)
    |VALUES (?,?, ?, ?)
    """.trimMargin(), 4) {
      bindString(1, id)
      bindString(2, name)
      bindString(3, description)
      bindLong(4, if (sync) 1L else 0L)
    }
    notifyQueries(-1650290033, {database.todometerQueries.selectTaskList +
        database.todometerQueries.selectAllTaskLists})
  }

  public override fun updateTaskList(
    name: String,
    description: String,
    sync: Boolean,
    id: String
  ): Unit {
    driver.execute(-1730872552, """
    |UPDATE TaskListEntity
    |SET
    |    name = ?,
    |    description = ?,
    |    sync = ?
    |WHERE id = ?
    """.trimMargin(), 4) {
      bindString(1, name)
      bindString(2, description)
      bindLong(3, if (sync) 1L else 0L)
      bindString(4, id)
    }
    notifyQueries(-1730872552, {database.todometerQueries.selectTaskList +
        database.todometerQueries.selectAllTaskLists + database.todometerQueries.selectTask +
        database.todometerQueries.selectTasksByTaskListId +
        database.todometerQueries.selectAllTasks})
  }

  public override fun updateTaskListName(name: String, id: String): Unit {
    driver.execute(-514417213, """
    |UPDATE TaskListEntity
    |SET
    |    name = ?
    |WHERE id = ?
    """.trimMargin(), 2) {
      bindString(1, name)
      bindString(2, id)
    }
    notifyQueries(-514417213, {database.todometerQueries.selectTaskList +
        database.todometerQueries.selectAllTaskLists + database.todometerQueries.selectTask +
        database.todometerQueries.selectTasksByTaskListId +
        database.todometerQueries.selectAllTasks})
  }

  public override fun deleteTaskList(id: String): Unit {
    driver.execute(-359053830, """
    |DELETE FROM TaskListEntity
    |WHERE id = ?
    """.trimMargin(), 1) {
      bindString(1, id)
    }
    notifyQueries(-359053830, {database.todometerQueries.selectTaskList +
        database.todometerQueries.selectAllTaskLists + database.todometerQueries.selectTask +
        database.todometerQueries.selectTasksByTaskListId +
        database.todometerQueries.selectAllTasks})
  }

  private inner class SelectTaskQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectTask, mapper) {
    public override fun execute(): SqlCursor = driver.executeQuery(-437404147, """
    |SELECT *
    |FROM TaskEntity
    |WHERE id = ?
    """.trimMargin(), 1) {
      bindString(1, id)
    }

    public override fun toString(): String = "Todometer.sq:selectTask"
  }

  private inner class SelectTasksByTaskListIdQuery<out T : Any>(
    public val tasklist_id: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectTasksByTaskListId, mapper) {
    public override fun execute(): SqlCursor = driver.executeQuery(468867867, """
    |SELECT *
    |FROM TaskEntity
    |WHERE tasklist_id = ?
    """.trimMargin(), 1) {
      bindString(1, tasklist_id)
    }

    public override fun toString(): String = "Todometer.sq:selectTasksByTaskListId"
  }

  private inner class SelectTaskListQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectTaskList, mapper) {
    public override fun execute(): SqlCursor = driver.executeQuery(-1648749493, """
    |SELECT *
    |FROM TaskListEntity
    |WHERE id = ?
    """.trimMargin(), 1) {
      bindString(1, id)
    }

    public override fun toString(): String = "Todometer.sq:selectTaskList"
  }
}
