package dev.sergiobelda.todometer.common.data.api.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskStateRequestBody(
    val state: String
)
