package com.example.memorykeeper.data.remote

import androidx.compose.foundation.MutatePriority
import kotlinx.serialization.Serializable


@Serializable
data class NoteDto(
    val id: String,
    val user_id: String? = null,
    val userName: String = "",
    val title: String = "",
    val content: String ? = null,
    val category: String? = null,
    val is_completed: Boolean = false,
    val section_type: String = "",
    val created_at: String? = null,
    val updated_at: String? = null,
    val priority: Int? = 0
)

