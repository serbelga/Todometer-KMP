package com.sergiobelda.todometer.common.model

data class Tag(
    val id: String,
    val color: Color,
    val name: String,
    val sync: Boolean
)
