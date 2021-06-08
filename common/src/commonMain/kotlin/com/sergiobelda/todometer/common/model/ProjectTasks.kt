package com.sergiobelda.todometer.common.model

data class ProjectTasks(
    val id: String,
    val name: String,
    val description: String,
    val tasks: List<TaskTag> = arrayListOf(),
    val sync: Boolean
) {
    override fun toString() = name
}
