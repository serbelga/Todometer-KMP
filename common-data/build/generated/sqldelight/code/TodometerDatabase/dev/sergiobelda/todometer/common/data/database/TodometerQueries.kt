package dev.sergiobelda.todometer.common.`data`.database

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import dev.sergiobelda.todometer.common.domain.model.Tag
import dev.sergiobelda.todometer.common.domain.model.TaskState
import kotlin.Any
import kotlin.Boolean
import kotlin.String
import kotlin.Unit

public interface TodometerQueries : Transacter {
  public fun <T : Any> selectTask(id: String, mapper: (
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean
  ) -> T): Query<T>

  public fun selectTask(id: String): Query<TaskEntity>

  public fun <T : Any> selectTasksByTaskListId(tasklist_id: String, mapper: (
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean
  ) -> T): Query<T>

  public fun selectTasksByTaskListId(tasklist_id: String): Query<TaskEntity>

  public fun <T : Any> selectAllTasks(mapper: (
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean
  ) -> T): Query<T>

  public fun selectAllTasks(): Query<TaskEntity>

  public fun <T : Any> selectTaskList(id: String, mapper: (
    id: String,
    name: String,
    description: String,
    sync: Boolean
  ) -> T): Query<T>

  public fun selectTaskList(id: String): Query<TaskListEntity>

  public fun <T : Any> selectAllTaskLists(mapper: (
    id: String,
    name: String,
    description: String,
    sync: Boolean
  ) -> T): Query<T>

  public fun selectAllTaskLists(): Query<TaskListEntity>

  public fun insertOrReplaceTask(
    id: String,
    title: String,
    description: String?,
    state: TaskState,
    tasklist_id: String,
    tag: Tag,
    sync: Boolean
  ): Unit

  public fun updateTask(
    title: String,
    description: String?,
    tag: Tag,
    id: String
  ): Unit

  public fun updateTaskSync(sync: Boolean, id: String): Unit

  public fun updateTaskState(state: TaskState, id: String): Unit

  public fun deleteTask(id: String): Unit

  public fun insertTaskList(
    id: String,
    name: String,
    description: String,
    sync: Boolean
  ): Unit

  public fun insertOrReplaceTaskList(
    id: String,
    name: String,
    description: String,
    sync: Boolean
  ): Unit

  public fun updateTaskList(
    name: String,
    description: String,
    sync: Boolean,
    id: String
  ): Unit

  public fun updateTaskListName(name: String, id: String): Unit

  public fun deleteTaskList(id: String): Unit
}
