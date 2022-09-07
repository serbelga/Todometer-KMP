package dev.sergiobelda.todometer.common.database.commondatabase

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.sergiobelda.todometer.common.database.PragmaQueries
import dev.sergiobelda.todometer.common.database.TaskChecklistItemEntity
import dev.sergiobelda.todometer.common.database.TaskChecklistItemEntityQueries
import dev.sergiobelda.todometer.common.database.TaskEntity
import dev.sergiobelda.todometer.common.database.TaskEntityQueries
import dev.sergiobelda.todometer.common.database.TaskListEntityQueries
import dev.sergiobelda.todometer.common.database.TodometerDatabase
import kotlin.Int
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<TodometerDatabase>.schema: SqlSchema
  get() = TodometerDatabaseImpl.Schema

internal fun KClass<TodometerDatabase>.newInstance(
  driver: SqlDriver,
  TaskChecklistItemEntityAdapter: TaskChecklistItemEntity.Adapter,
  TaskEntityAdapter: TaskEntity.Adapter,
): TodometerDatabase = TodometerDatabaseImpl(driver, TaskChecklistItemEntityAdapter,
    TaskEntityAdapter)

private class TodometerDatabaseImpl(
  driver: SqlDriver,
  TaskChecklistItemEntityAdapter: TaskChecklistItemEntity.Adapter,
  TaskEntityAdapter: TaskEntity.Adapter,
) : TransacterImpl(driver), TodometerDatabase {
  public override val pragmaQueries: PragmaQueries = PragmaQueries(driver)

  public override val taskChecklistItemEntityQueries: TaskChecklistItemEntityQueries =
      TaskChecklistItemEntityQueries(driver, TaskChecklistItemEntityAdapter)

  public override val taskEntityQueries: TaskEntityQueries = TaskEntityQueries(driver,
      TaskEntityAdapter)

  public override val taskListEntityQueries: TaskListEntityQueries = TaskListEntityQueries(driver)

  public object Schema : SqlSchema {
    public override val version: Int
      get() = 3

    public override fun create(driver: SqlDriver): QueryResult<Unit> {
      driver.execute(null, """
          |CREATE TABLE TaskChecklistItemEntity (
          |    id TEXT PRIMARY KEY NOT NULL,
          |    text TEXT NOT NULL,
          |    task_id TEXT NOT NULL,
          |    state TEXT NOT NULL,
          |    FOREIGN KEY(task_id) REFERENCES TaskEntity(id) ON UPDATE CASCADE ON DELETE CASCADE
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
          |    dueDate INTEGER,
          |    FOREIGN KEY(tasklist_id) REFERENCES TaskListEntity(id) ON UPDATE CASCADE ON DELETE CASCADE
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE TaskListEntity (
          |    id TEXT PRIMARY KEY NOT NULL,
          |    name TEXT NOT NULL,
          |    description TEXT NOT NULL,
          |    sync INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    public override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int,
    ): QueryResult<Unit> {
      if (oldVersion <= 1 && newVersion > 1) {
        driver.execute(null, "ALTER TABLE TaskEntity ADD COLUMN dueDate INTEGER", 0)
      }
      if (oldVersion <= 2 && newVersion > 2) {
        driver.execute(null, """
            |CREATE TABLE TaskChecklistItemEntity (
            |    id TEXT PRIMARY KEY NOT NULL,
            |    text TEXT NOT NULL,
            |    task_id TEXT NOT NULL,
            |    state TEXT NOT NULL,
            |    FOREIGN KEY(task_id) REFERENCES TaskEntity(id) ON UPDATE CASCADE ON DELETE CASCADE
            |)
            """.trimMargin(), 0)
      }
      return QueryResult.Unit
    }
  }
}
