package com.sergiobelda.todometer.common.api.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskStateRequestBody(
    val state: String
)
