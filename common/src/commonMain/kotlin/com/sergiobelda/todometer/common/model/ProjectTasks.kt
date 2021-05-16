package com.sergiobelda.todometer.common.model

data class ProjectTasks(
    val id: Long = 0,
    val name: String,
    val description: String,
    val tasks: List<TaskTag> = arrayListOf()
) {
    override fun toString() = name
}
