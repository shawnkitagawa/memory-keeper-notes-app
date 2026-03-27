package com.example.memorykeeper.data.remote

import kotlinx.serialization.Serializable


@Serializable
data class ProfileDto(
    val user_id: String,
    val username: String,
)
