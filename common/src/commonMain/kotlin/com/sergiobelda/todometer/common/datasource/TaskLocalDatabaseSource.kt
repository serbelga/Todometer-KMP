package com.sergiobelda.todometer.common.datasource

import com.sergiobelda.todometer.common.database.dao.ITaskDao
import com.sergiobelda.todometer.common.database.mapper.TaskMapper.toDomain
import com.sergiobelda.todometer.common.database.mapper.TaskMapper.toEntity
import com.sergiobelda.todometer.common.model.Task
import com.sergiobelda.todometer.common.model.TaskState
import com.sergiobelda.todometer.common.model.TaskTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskLocalDatabaseSource(
    private val taskDao: ITaskDao
) : ITaskLocalDatabaseSource {

    override fun getTask(id: Long): Flow<Result<TaskTag?>> =
        taskDao.getTask(id).map { Result.Success(it?.toDomain()) }

    override fun getTasks(): Flow<Result<List<TaskTag>>> =
        taskDao.getTasks().map { list ->
            Result.Success(list.map { it.toDomain() })
        }

    override suspend fun insertTask(task: Task) =
        taskDao.insertTask(task.toEntity())

    override suspend fun updateTask(task: Task) =
        taskDao.updateTask(task.toEntity())

    override suspend fun updateTaskState(id: Long, state: TaskState) =
        taskDao.updateTaskState(id, state)

    override suspend fun deleteTask(id: Long) =
        taskDao.deleteTask(id)
}
