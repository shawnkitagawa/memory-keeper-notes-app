package com.example.memorykeeper.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class insertData(
    val title : String,
    val content: String?,
    val category: String,
    val section_type: String,
    val priority: Int?,
    val user_name: String,
    val user_id: String,
)
