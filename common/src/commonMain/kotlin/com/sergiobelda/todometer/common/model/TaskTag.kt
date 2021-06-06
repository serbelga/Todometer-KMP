package com.sergiobelda.todometer.common.model

data class TaskTag(
    val id: String,
    val title: String,
    val description: String?,
    val state: TaskState = TaskState.DOING,
    val projectId: String,
    val tag: Tag?,
    val sync: Boolean
)
