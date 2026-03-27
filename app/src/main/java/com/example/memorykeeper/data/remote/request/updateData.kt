package com.example.memorykeeper.data.remote.request

import kotlinx.serialization.Serializable


@Serializable
data class updateData (
    val title: String,
    val content: String?,
    val category: String,
    val section_type: String,
    val priority: Int?,
    val user_name: String,
    val user_id: String,
    val id: String,
    val is_completed: Boolean
)
