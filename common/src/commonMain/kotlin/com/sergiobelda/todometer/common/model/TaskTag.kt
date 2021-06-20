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

fun TaskTag.toTask() =
    Task(
        id = id,
        title = title,
        description = description,
        state = state,
        projectId = projectId,
        tagId = tag?.id,
        sync = sync
    )
