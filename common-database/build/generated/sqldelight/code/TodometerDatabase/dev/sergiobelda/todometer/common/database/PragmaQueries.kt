package dev.sergiobelda.todometer.common.database

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.SqlDriver
import kotlin.Unit

public class PragmaQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun pragmaForeignKeysOff(): Unit {
    driver.execute(7146242, """PRAGMA foreign_keys = 0""", 0)
  }

  public fun pragmaForeignKeysOn(): Unit {
    driver.execute(-692506132, """PRAGMA foreign_keys = 1""", 0)
  }
}
