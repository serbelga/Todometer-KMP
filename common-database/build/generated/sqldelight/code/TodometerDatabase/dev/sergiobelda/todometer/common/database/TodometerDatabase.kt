package dev.sergiobelda.todometer.common.database

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import dev.sergiobelda.todometer.common.database.commondatabase.newInstance
import dev.sergiobelda.todometer.common.database.commondatabase.schema

public interface TodometerDatabase : Transacter {
  public val pragmaQueries: PragmaQueries

  public val taskChecklistItemEntityQueries: TaskChecklistItemEntityQueries

  public val taskEntityQueries: TaskEntityQueries

  public val taskListEntityQueries: TaskListEntityQueries

  public companion object {
    public val Schema: SqlSchema
      get() = TodometerDatabase::class.schema

    public operator fun invoke(
      driver: SqlDriver,
      TaskChecklistItemEntityAdapter: TaskChecklistItemEntity.Adapter,
      TaskEntityAdapter: TaskEntity.Adapter,
    ): TodometerDatabase = TodometerDatabase::class.newInstance(driver,
        TaskChecklistItemEntityAdapter, TaskEntityAdapter)
  }
}
