package dev.sergiobelda.todometer.common.`data`.database

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import dev.sergiobelda.todometer.common.`data`.database.commondata.newInstance
import dev.sergiobelda.todometer.common.`data`.database.commondata.schema

public interface TodometerDatabase : Transacter {
  public val todometerQueries: TodometerQueries

  public companion object {
    public val Schema: SqlDriver.Schema
      get() = TodometerDatabase::class.schema

    public operator fun invoke(driver: SqlDriver, TaskEntityAdapter: TaskEntity.Adapter):
        TodometerDatabase = TodometerDatabase::class.newInstance(driver, TaskEntityAdapter)
  }
}
