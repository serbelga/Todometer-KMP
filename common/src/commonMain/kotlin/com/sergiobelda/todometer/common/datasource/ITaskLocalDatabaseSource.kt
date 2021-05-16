package com.sergiobelda.todometer.common.datasource

import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.model.TaskState
import com.sergiobelda.todometer.common.model.TaskTag
import kotlinx.coroutines.flow.Flow

interface ITaskLocalDatabaseSource {

    fun getTask(id: Long): Flow<Result<TaskTag?>>

    fun getTasks(): Flow<Result<List<TaskTag>>>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun updateTaskState(id: Long, state: TaskState)

    suspend fun deleteTask(id: Long)
}
