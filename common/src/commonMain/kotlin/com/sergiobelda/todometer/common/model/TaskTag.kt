package com.sergiobelda.todometer.common.model

data class TaskTag(
    val id: Long = 0,
    val title: String,
    val description: String?,
    val state: TaskState = TaskState.DOING,
    val projectId: Long,
    val tag: Tag
)
