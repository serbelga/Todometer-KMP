package dev.sergiobelda.todometer.common.domain.model

data class TaskItem(
    val id: String,
    val title: String,
    val description: String?,
    val state: TaskState,
    val tasklist_id: String,
    val tag: Tag,
    val sync: Boolean,
    val dueDate: Long?,
    val checklistItemsDone: Long,
    val totalChecklistItems: Long
)
